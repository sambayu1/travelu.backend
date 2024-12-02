package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Diskon;
import travelu.travelu_backend.model.DiskonDTO;
import travelu.travelu_backend.repos.AdminRepository;
import travelu.travelu_backend.repos.DiskonRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
@Transactional
public class DiskonService {

    private final DiskonRepository diskonRepository;
    private final PemesananRepository pemesananRepository;
    private final AdminRepository adminRepository;

    public DiskonService(final DiskonRepository diskonRepository,
            final PemesananRepository pemesananRepository, final AdminRepository adminRepository) {
        this.diskonRepository = diskonRepository;
        this.pemesananRepository = pemesananRepository;
        this.adminRepository = adminRepository;
    }

    public List<DiskonDTO> findAll() {
        final List<Diskon> diskons = diskonRepository.findAll(Sort.by("id"));
        return diskons.stream()
                .map(diskon -> mapToDTO(diskon, new DiskonDTO()))
                .toList();
    }

    public DiskonDTO get(final Long id) {
        return diskonRepository.findById(id)
                .map(diskon -> mapToDTO(diskon, new DiskonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DiskonDTO diskonDTO) {
        final Diskon diskon = new Diskon();
        mapToEntity(diskonDTO, diskon);
        return diskonRepository.save(diskon).getId();
    }

    public void update(final Long id, final DiskonDTO diskonDTO) {
        final Diskon diskon = diskonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(diskonDTO, diskon);
        diskonRepository.save(diskon);
    }

    public void delete(final Long id) {
        final Diskon diskon = diskonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        pemesananRepository.findAllByListDiskon(diskon)
                .forEach(pemesanan -> pemesanan.getListDiskon().remove(diskon));
        adminRepository.findAllByListDiskon(diskon)
                .forEach(admin -> admin.getListDiskon().remove(diskon));
        diskonRepository.delete(diskon);
    }

    private DiskonDTO mapToDTO(final Diskon diskon, final DiskonDTO diskonDTO) {
        diskonDTO.setId(diskon.getId());
        diskonDTO.setNama(diskon.getNama());
        diskonDTO.setHarga(diskon.getHarga());
        diskonDTO.setPercent(diskon.getPercent());
        diskonDTO.setCode(diskon.getCode());
        return diskonDTO;
    }

    private Diskon mapToEntity(final DiskonDTO diskonDTO, final Diskon diskon) {
        diskon.setNama(diskonDTO.getNama());
        diskon.setHarga(diskonDTO.getHarga());
        diskon.setPercent(diskonDTO.getPercent());
        diskon.setCode(diskonDTO.getCode());
        return diskon;
    }

}
