package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Admin;
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
    private final AdminRepository adminRepository;
    private final PemesananRepository pemesananRepository;

    public DiskonService(final DiskonRepository diskonRepository,
            final AdminRepository adminRepository, final PemesananRepository pemesananRepository) {
        this.diskonRepository = diskonRepository;
        this.adminRepository = adminRepository;
        this.pemesananRepository = pemesananRepository;
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
        diskonRepository.delete(diskon);
    }

    private DiskonDTO mapToDTO(final Diskon diskon, final DiskonDTO diskonDTO) {
        diskonDTO.setId(diskon.getId());
        diskonDTO.setNama(diskon.getNama());
        diskonDTO.setHarga(diskon.getHarga());
        diskonDTO.setPercent(diskon.getPercent());
        diskonDTO.setCode(diskon.getCode());
        diskonDTO.setRoleAdmin(diskon.getRoleAdmin().stream()
                .map(admin -> admin.getId())
                .toList());
        return diskonDTO;
    }

    private Diskon mapToEntity(final DiskonDTO diskonDTO, final Diskon diskon) {
        diskon.setNama(diskonDTO.getNama());
        diskon.setHarga(diskonDTO.getHarga());
        diskon.setPercent(diskonDTO.getPercent());
        diskon.setCode(diskonDTO.getCode());
        final List<Admin> roleAdmin = adminRepository.findAllById(
                diskonDTO.getRoleAdmin() == null ? Collections.emptyList() : diskonDTO.getRoleAdmin());
        if (roleAdmin.size() != (diskonDTO.getRoleAdmin() == null ? 0 : diskonDTO.getRoleAdmin().size())) {
            throw new NotFoundException("one of roleAdmin not found");
        }
        diskon.setRoleAdmin(new HashSet<>(roleAdmin));
        return diskon;
    }

}
