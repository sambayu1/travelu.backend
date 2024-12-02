package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Admin;
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Csticket;
import travelu.travelu_backend.domain.Diskon;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.domain.SuperAdmin;
import travelu.travelu_backend.model.AdminDTO;
import travelu.travelu_backend.repos.AdminRepository;
import travelu.travelu_backend.repos.ArmadaRepository;
import travelu.travelu_backend.repos.CsticketRepository;
import travelu.travelu_backend.repos.DiskonRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.repos.SuperAdminRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final DiskonRepository diskonRepository;
    private final ArmadaRepository armadaRepository;
    private final CsticketRepository csticketRepository;
    private final JadwalRepository jadwalRepository;
    private final SuperAdminRepository superAdminRepository;

    public AdminService(final AdminRepository adminRepository,
            final DiskonRepository diskonRepository, final ArmadaRepository armadaRepository,
            final CsticketRepository csticketRepository, final JadwalRepository jadwalRepository,
            final SuperAdminRepository superAdminRepository) {
        this.adminRepository = adminRepository;
        this.diskonRepository = diskonRepository;
        this.armadaRepository = armadaRepository;
        this.csticketRepository = csticketRepository;
        this.jadwalRepository = jadwalRepository;
        this.superAdminRepository = superAdminRepository;
    }

    public List<AdminDTO> findAll() {
        final List<Admin> admins = adminRepository.findAll(Sort.by("id"));
        return admins.stream()
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .toList();
    }

    public AdminDTO get(final Long id) {
        return adminRepository.findById(id)
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdminDTO adminDTO) {
        final Admin admin = new Admin();
        mapToEntity(adminDTO, admin);
        return adminRepository.save(admin).getId();
    }

    public void update(final Long id, final AdminDTO adminDTO) {
        final Admin admin = adminRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(adminDTO, admin);
        adminRepository.save(admin);
    }

    public void delete(final Long id) {
        adminRepository.deleteById(id);
    }

    private AdminDTO mapToDTO(final Admin admin, final AdminDTO adminDTO) {
        adminDTO.setId(admin.getId());
        adminDTO.setName(admin.getName());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setPassword(admin.getPassword());
        adminDTO.setRole(admin.getRole());
        adminDTO.setListDiskon(admin.getListDiskon().stream()
                .map(diskon -> diskon.getId())
                .toList());
        adminDTO.setListArmada(admin.getListArmada().stream()
                .map(armada -> armada.getId())
                .toList());
        adminDTO.setListComplain(admin.getListComplain().stream()
                .map(csticket -> csticket.getId())
                .toList());
        adminDTO.setListJadwal(admin.getListJadwal().stream()
                .map(jadwal -> jadwal.getId())
                .toList());
        return adminDTO;
    }

    private Admin mapToEntity(final AdminDTO adminDTO, final Admin admin) {
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setRole(adminDTO.getRole());
        final List<Diskon> listDiskon = diskonRepository.findAllById(
                adminDTO.getListDiskon() == null ? Collections.emptyList() : adminDTO.getListDiskon());
        if (listDiskon.size() != (adminDTO.getListDiskon() == null ? 0 : adminDTO.getListDiskon().size())) {
            throw new NotFoundException("one of listDiskon not found");
        }
        admin.setListDiskon(new HashSet<>(listDiskon));
        final List<Armada> listArmada = armadaRepository.findAllById(
                adminDTO.getListArmada() == null ? Collections.emptyList() : adminDTO.getListArmada());
        if (listArmada.size() != (adminDTO.getListArmada() == null ? 0 : adminDTO.getListArmada().size())) {
            throw new NotFoundException("one of listArmada not found");
        }
        admin.setListArmada(new HashSet<>(listArmada));
        final List<Csticket> listComplain = csticketRepository.findAllById(
                adminDTO.getListComplain() == null ? Collections.emptyList() : adminDTO.getListComplain());
        if (listComplain.size() != (adminDTO.getListComplain() == null ? 0 : adminDTO.getListComplain().size())) {
            throw new NotFoundException("one of listComplain not found");
        }
        admin.setListComplain(new HashSet<>(listComplain));
        final List<Jadwal> listJadwal = jadwalRepository.findAllById(
                adminDTO.getListJadwal() == null ? Collections.emptyList() : adminDTO.getListJadwal());
        if (listJadwal.size() != (adminDTO.getListJadwal() == null ? 0 : adminDTO.getListJadwal().size())) {
            throw new NotFoundException("one of listJadwal not found");
        }
        admin.setListJadwal(new HashSet<>(listJadwal));
        return admin;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Admin admin = adminRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SuperAdmin adminIdSuperAdmin = superAdminRepository.findFirstByAdminId(admin);
        if (adminIdSuperAdmin != null) {
            referencedWarning.setKey("admin.superAdmin.adminId.referenced");
            referencedWarning.addParam(adminIdSuperAdmin.getId());
            return referencedWarning;
        }
        return null;
    }

}
