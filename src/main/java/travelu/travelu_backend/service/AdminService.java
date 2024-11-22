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
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.model.AdminDTO;
import travelu.travelu_backend.repos.AdminRepository;
import travelu.travelu_backend.repos.ArmadaRepository;
import travelu.travelu_backend.repos.CsticketRepository;
import travelu.travelu_backend.repos.DiskonRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final ArmadaRepository armadaRepository;
    private final CsticketRepository csticketRepository;
    private final JadwalRepository jadwalRepository;
    private final PemesananRepository pemesananRepository;
    private final DiskonRepository diskonRepository;

    public AdminService(final AdminRepository adminRepository,
            final ArmadaRepository armadaRepository, final CsticketRepository csticketRepository,
            final JadwalRepository jadwalRepository, final PemesananRepository pemesananRepository,
            final DiskonRepository diskonRepository) {
        this.adminRepository = adminRepository;
        this.armadaRepository = armadaRepository;
        this.csticketRepository = csticketRepository;
        this.jadwalRepository = jadwalRepository;
        this.pemesananRepository = pemesananRepository;
        this.diskonRepository = diskonRepository;
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
        final Admin admin = adminRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        pemesananRepository.findAllByRoleAdmin(admin)
                .forEach(pemesanan -> pemesanan.getRoleAdmin().remove(admin));
        diskonRepository.findAllByRoleAdmin(admin)
                .forEach(diskon -> diskon.getRoleAdmin().remove(admin));
        adminRepository.delete(admin);
    }

    private AdminDTO mapToDTO(final Admin admin, final AdminDTO adminDTO) {
        adminDTO.setId(admin.getId());
        adminDTO.setUsersId(admin.getUsersId());
        adminDTO.setListArmada(admin.getListArmada().stream()
                .map(armada -> armada.getId())
                .toList());
        adminDTO.setCsTicket(admin.getCsTicket().stream()
                .map(csticket -> csticket.getId())
                .toList());
        adminDTO.setTanggalJadwal(admin.getTanggalJadwal().stream()
                .map(jadwal -> jadwal.getId())
                .toList());
        return adminDTO;
    }

    private Admin mapToEntity(final AdminDTO adminDTO, final Admin admin) {
        admin.setUsersId(adminDTO.getUsersId());
        final List<Armada> listArmada = armadaRepository.findAllById(
                adminDTO.getListArmada() == null ? Collections.emptyList() : adminDTO.getListArmada());
        if (listArmada.size() != (adminDTO.getListArmada() == null ? 0 : adminDTO.getListArmada().size())) {
            throw new NotFoundException("one of listArmada not found");
        }
        admin.setListArmada(new HashSet<>(listArmada));
        final List<Csticket> csTicket = csticketRepository.findAllById(
                adminDTO.getCsTicket() == null ? Collections.emptyList() : adminDTO.getCsTicket());
        if (csTicket.size() != (adminDTO.getCsTicket() == null ? 0 : adminDTO.getCsTicket().size())) {
            throw new NotFoundException("one of csTicket not found");
        }
        admin.setCsTicket(new HashSet<>(csTicket));
        final List<Jadwal> tanggalJadwal = jadwalRepository.findAllById(
                adminDTO.getTanggalJadwal() == null ? Collections.emptyList() : adminDTO.getTanggalJadwal());
        if (tanggalJadwal.size() != (adminDTO.getTanggalJadwal() == null ? 0 : adminDTO.getTanggalJadwal().size())) {
            throw new NotFoundException("one of tanggalJadwal not found");
        }
        admin.setTanggalJadwal(new HashSet<>(tanggalJadwal));
        return admin;
    }

}
