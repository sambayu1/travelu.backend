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
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.repos.PelangganRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
@Transactional
public class JadwalService {

    private final JadwalRepository jadwalRepository;
    private final ArmadaRepository armadaRepository;
    private final PelangganRepository pelangganRepository;
    private final AdminRepository adminRepository;
    private final PemesananRepository pemesananRepository;

    public JadwalService(final JadwalRepository jadwalRepository,
            final ArmadaRepository armadaRepository, final PelangganRepository pelangganRepository,
            final AdminRepository adminRepository, final PemesananRepository pemesananRepository) {
        this.jadwalRepository = jadwalRepository;
        this.armadaRepository = armadaRepository;
        this.pelangganRepository = pelangganRepository;
        this.adminRepository = adminRepository;
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
        pelangganRepository.findAllByTanggalJadwal(jadwal)
                .forEach(pelanggan -> pelanggan.getTanggalJadwal().remove(jadwal));
        adminRepository.findAllByTanggalJadwal(jadwal)
                .forEach(admin -> admin.getTanggalJadwal().remove(jadwal));
        jadwalRepository.delete(jadwal);
    }

    private JadwalDTO mapToDTO(final Jadwal jadwal, final JadwalDTO jadwalDTO) {
        jadwalDTO.setId(jadwal.getId());
        jadwalDTO.setArmadaId(jadwal.getArmadaId());
        jadwalDTO.setHari(jadwal.getHari());
        jadwalDTO.setTanggal(jadwal.getTanggal());
        jadwalDTO.setBulan(jadwal.getBulan());
        jadwalDTO.setTahun(jadwal.getTahun());
        jadwalDTO.setWaktuKeberagkatan(jadwal.getWaktuKeberagkatan());
        jadwalDTO.setLokasiKeberangkatan(jadwal.getLokasiKeberangkatan());
        jadwalDTO.setTujuan(jadwal.getTujuan());
        jadwalDTO.setHargaTiket(jadwal.getHargaTiket());
        jadwalDTO.setListArmada(jadwal.getListArmada() == null ? null : jadwal.getListArmada().getId());
        return jadwalDTO;
    }

    private Jadwal mapToEntity(final JadwalDTO jadwalDTO, final Jadwal jadwal) {
        jadwal.setArmadaId(jadwalDTO.getArmadaId());
        jadwal.setHari(jadwalDTO.getHari());
        jadwal.setTanggal(jadwalDTO.getTanggal());
        jadwal.setBulan(jadwalDTO.getBulan());
        jadwal.setTahun(jadwalDTO.getTahun());
        jadwal.setWaktuKeberagkatan(jadwalDTO.getWaktuKeberagkatan());
        jadwal.setLokasiKeberangkatan(jadwalDTO.getLokasiKeberangkatan());
        jadwal.setTujuan(jadwalDTO.getTujuan());
        jadwal.setHargaTiket(jadwalDTO.getHargaTiket());
        final Armada listArmada = jadwalDTO.getListArmada() == null ? null : armadaRepository.findById(jadwalDTO.getListArmada())
                .orElseThrow(() -> new NotFoundException("listArmada not found"));
        jadwal.setListArmada(listArmada);
        return jadwal;
    }

    public boolean listArmadaExists(final Long id) {
        return jadwalRepository.existsByListArmadaId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Jadwal jadwal = jadwalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Pemesanan tanggalJadwalPemesanan = pemesananRepository.findFirstByTanggalJadwal(jadwal);
        if (tanggalJadwalPemesanan != null) {
            referencedWarning.setKey("jadwal.pemesanan.tanggalJadwal.referenced");
            referencedWarning.addParam(tanggalJadwalPemesanan.getId());
            return referencedWarning;
        }
        return null;
    }

}
