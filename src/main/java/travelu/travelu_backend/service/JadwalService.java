package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.domain.Pemesanan;
import travelu.travelu_backend.model.JadwalDTO;
import travelu.travelu_backend.repos.AdminRepository;
import travelu.travelu_backend.repos.ArmadaRepository;
import travelu.travelu_backend.repos.ItinerariRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
@Transactional
public class JadwalService {

    private final JadwalRepository jadwalRepository;
    private final ArmadaRepository armadaRepository;
    private final AdminRepository adminRepository;
    private final ItinerariRepository itinerariRepository;
    private final PemesananRepository pemesananRepository;

    public JadwalService(final JadwalRepository jadwalRepository,
            final ArmadaRepository armadaRepository, final AdminRepository adminRepository,
            final ItinerariRepository itinerariRepository,
            final PemesananRepository pemesananRepository) {
        this.jadwalRepository = jadwalRepository;
        this.armadaRepository = armadaRepository;
        this.adminRepository = adminRepository;
        this.itinerariRepository = itinerariRepository;
        this.pemesananRepository = pemesananRepository;
    }

    public List<JadwalDTO> findAll() {
        final List<Jadwal> jadwals = jadwalRepository.findAll(Sort.by("id"));
        return jadwals.stream()
                .map(jadwal -> mapToDTO(jadwal, new JadwalDTO()))
                .toList();
    }

    public JadwalDTO get(final Long id) {
        return jadwalRepository.findById(id)
                .map(jadwal -> mapToDTO(jadwal, new JadwalDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JadwalDTO jadwalDTO) {
        final Jadwal jadwal = new Jadwal();
        mapToEntity(jadwalDTO, jadwal);
        return jadwalRepository.save(jadwal).getId();
    }

    public void update(final Long id, final JadwalDTO jadwalDTO) {
        final Jadwal jadwal = jadwalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(jadwalDTO, jadwal);
        jadwalRepository.save(jadwal);
    }

    public void delete(final Long id) {
        final Jadwal jadwal = jadwalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        adminRepository.findAllByListJadwal(jadwal)
                .forEach(admin -> admin.getListJadwal().remove(jadwal));
        itinerariRepository.findAllByListJadwal(jadwal)
                .forEach(itinerari -> itinerari.getListJadwal().remove(jadwal));
        jadwalRepository.delete(jadwal);
    }

    private JadwalDTO mapToDTO(final Jadwal jadwal, final JadwalDTO jadwalDTO) {
        jadwalDTO.setId(jadwal.getId());
        jadwalDTO.setTanggal(jadwal.getTanggal());
        jadwalDTO.setWaktu(jadwal.getWaktu());
        jadwalDTO.setAsal(jadwal.getAsal());
        jadwalDTO.setDestinasi(jadwal.getDestinasi());
        jadwalDTO.setHargaTiket(jadwal.getHargaTiket());
        jadwalDTO.setArmadaId(jadwal.getArmadaId() == null ? null : jadwal.getArmadaId().getId());
        return jadwalDTO;
    }

    private Jadwal mapToEntity(final JadwalDTO jadwalDTO, final Jadwal jadwal) {
        jadwal.setTanggal(jadwalDTO.getTanggal());
        jadwal.setWaktu(jadwalDTO.getWaktu());
        jadwal.setAsal(jadwalDTO.getAsal());
        jadwal.setDestinasi(jadwalDTO.getDestinasi());
        jadwal.setHargaTiket(jadwalDTO.getHargaTiket());
        final Armada armadaId = jadwalDTO.getArmadaId() == null ? null : armadaRepository.findById(jadwalDTO.getArmadaId())
                .orElseThrow(() -> new NotFoundException("armadaId not found"));
        jadwal.setArmadaId(armadaId);
        return jadwal;
    }

    public boolean armadaIdExists(final Long id) {
        return jadwalRepository.existsByArmadaIdId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Jadwal jadwal = jadwalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Pemesanan jadwalIdPemesanan = pemesananRepository.findFirstByJadwalId(jadwal);
        if (jadwalIdPemesanan != null) {
            referencedWarning.setKey("jadwal.pemesanan.jadwalId.referenced");
            referencedWarning.addParam(jadwalIdPemesanan.getId());
            return referencedWarning;
        }
        return null;
    }

}
