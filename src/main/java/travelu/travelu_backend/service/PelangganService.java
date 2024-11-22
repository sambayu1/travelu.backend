package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Csticket;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.domain.Pelanggan;
import travelu.travelu_backend.domain.Pemesanan;
import travelu.travelu_backend.model.PelangganDTO;
import travelu.travelu_backend.repos.CsticketRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.repos.PelangganRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
@Transactional
public class PelangganService {

    private final PelangganRepository pelangganRepository;
    private final JadwalRepository jadwalRepository;
    private final PemesananRepository pemesananRepository;
    private final CsticketRepository csticketRepository;

    public PelangganService(final PelangganRepository pelangganRepository,
            final JadwalRepository jadwalRepository, final PemesananRepository pemesananRepository,
            final CsticketRepository csticketRepository) {
        this.pelangganRepository = pelangganRepository;
        this.jadwalRepository = jadwalRepository;
        this.pemesananRepository = pemesananRepository;
        this.csticketRepository = csticketRepository;
    }

    public List<PelangganDTO> findAll() {
        final List<Pelanggan> pelanggans = pelangganRepository.findAll(Sort.by("id"));
        return pelanggans.stream()
                .map(pelanggan -> mapToDTO(pelanggan, new PelangganDTO()))
                .toList();
    }

    public PelangganDTO get(final Long id) {
        return pelangganRepository.findById(id)
                .map(pelanggan -> mapToDTO(pelanggan, new PelangganDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PelangganDTO pelangganDTO) {
        final Pelanggan pelanggan = new Pelanggan();
        mapToEntity(pelangganDTO, pelanggan);
        return pelangganRepository.save(pelanggan).getId();
    }

    public void update(final Long id, final PelangganDTO pelangganDTO) {
        final Pelanggan pelanggan = pelangganRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pelangganDTO, pelanggan);
        pelangganRepository.save(pelanggan);
    }

    public void delete(final Long id) {
        pelangganRepository.deleteById(id);
    }

    private PelangganDTO mapToDTO(final Pelanggan pelanggan, final PelangganDTO pelangganDTO) {
        pelangganDTO.setId(pelanggan.getId());
        pelangganDTO.setNama(pelanggan.getNama());
        pelangganDTO.setEmail(pelanggan.getEmail());
        pelangganDTO.setNoTelp(pelanggan.getNoTelp());
        pelangganDTO.setTanggalJadwal(pelanggan.getTanggalJadwal().stream()
                .map(jadwal -> jadwal.getId())
                .toList());
        return pelangganDTO;
    }

    private Pelanggan mapToEntity(final PelangganDTO pelangganDTO, final Pelanggan pelanggan) {
        pelanggan.setNama(pelangganDTO.getNama());
        pelanggan.setEmail(pelangganDTO.getEmail());
        pelanggan.setNoTelp(pelangganDTO.getNoTelp());
        final List<Jadwal> tanggalJadwal = jadwalRepository.findAllById(
                pelangganDTO.getTanggalJadwal() == null ? Collections.emptyList() : pelangganDTO.getTanggalJadwal());
        if (tanggalJadwal.size() != (pelangganDTO.getTanggalJadwal() == null ? 0 : pelangganDTO.getTanggalJadwal().size())) {
            throw new NotFoundException("one of tanggalJadwal not found");
        }
        pelanggan.setTanggalJadwal(new HashSet<>(tanggalJadwal));
        return pelanggan;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Pelanggan pelanggan = pelangganRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Pemesanan pelangganIdPemesanan = pemesananRepository.findFirstByPelangganId(pelanggan);
        if (pelangganIdPemesanan != null) {
            referencedWarning.setKey("pelanggan.pemesanan.pelangganId.referenced");
            referencedWarning.addParam(pelangganIdPemesanan.getId());
            return referencedWarning;
        }
        final Csticket pelangganIdCsticket = csticketRepository.findFirstByPelangganId(pelanggan);
        if (pelangganIdCsticket != null) {
            referencedWarning.setKey("pelanggan.csticket.pelangganId.referenced");
            referencedWarning.addParam(pelangganIdCsticket.getId());
            return referencedWarning;
        }
        return null;
    }

}
