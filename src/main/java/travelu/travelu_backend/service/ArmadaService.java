package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.model.ArmadaDTO;
import travelu.travelu_backend.repos.AdminRepository;
import travelu.travelu_backend.repos.ArmadaRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
@Transactional
public class ArmadaService {

    private final ArmadaRepository armadaRepository;
    private final AdminRepository adminRepository;
    private final JadwalRepository jadwalRepository;

    public ArmadaService(final ArmadaRepository armadaRepository,
            final AdminRepository adminRepository, final JadwalRepository jadwalRepository) {
        this.armadaRepository = armadaRepository;
        this.adminRepository = adminRepository;
        this.jadwalRepository = jadwalRepository;
    }

    public List<ArmadaDTO> findAll() {
        final List<Armada> armadas = armadaRepository.findAll(Sort.by("id"));
        return armadas.stream()
                .map(armada -> mapToDTO(armada, new ArmadaDTO()))
                .toList();
    }

    public ArmadaDTO get(final Long id) {
        return armadaRepository.findById(id)
                .map(armada -> mapToDTO(armada, new ArmadaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ArmadaDTO armadaDTO) {
        final Armada armada = new Armada();
        mapToEntity(armadaDTO, armada);
        return armadaRepository.save(armada).getId();
    }

    public void update(final Long id, final ArmadaDTO armadaDTO) {
        final Armada armada = armadaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(armadaDTO, armada);
        armadaRepository.save(armada);
    }

    public void delete(final Long id) {
        final Armada armada = armadaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        adminRepository.findAllByListArmada(armada)
                .forEach(admin -> admin.getListArmada().remove(armada));
        armadaRepository.delete(armada);
    }

    private ArmadaDTO mapToDTO(final Armada armada, final ArmadaDTO armadaDTO) {
        armadaDTO.setId(armada.getId());
        armadaDTO.setSupir(armada.getSupir());
        armadaDTO.setPlatNom(armada.getPlatNom());
        return armadaDTO;
    }

    private Armada mapToEntity(final ArmadaDTO armadaDTO, final Armada armada) {
        armada.setSupir(armadaDTO.getSupir());
        armada.setPlatNom(armadaDTO.getPlatNom());
        return armada;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Armada armada = armadaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Jadwal armadaIdJadwal = jadwalRepository.findFirstByArmadaId(armada);
        if (armadaIdJadwal != null) {
            referencedWarning.setKey("armada.jadwal.armadaId.referenced");
            referencedWarning.addParam(armadaIdJadwal.getId());
            return referencedWarning;
        }
        return null;
    }

}
