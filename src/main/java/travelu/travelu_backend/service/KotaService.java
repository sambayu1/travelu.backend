package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Cabang;
import travelu.travelu_backend.domain.Kota;
import travelu.travelu_backend.model.KotaDTO;
import travelu.travelu_backend.repos.CabangRepository;
import travelu.travelu_backend.repos.KotaRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
public class KotaService {

    private final KotaRepository kotaRepository;
    private final CabangRepository cabangRepository;

    public KotaService(final KotaRepository kotaRepository,
            final CabangRepository cabangRepository) {
        this.kotaRepository = kotaRepository;
        this.cabangRepository = cabangRepository;
    }

    public List<KotaDTO> findAll() {
        final List<Kota> kotas = kotaRepository.findAll(Sort.by("id"));
        return kotas.stream()
                .map(kota -> mapToDTO(kota, new KotaDTO()))
                .toList();
    }

    public KotaDTO get(final Long id) {
        return kotaRepository.findById(id)
                .map(kota -> mapToDTO(kota, new KotaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final KotaDTO kotaDTO) {
        final Kota kota = new Kota();
        mapToEntity(kotaDTO, kota);
        return kotaRepository.save(kota).getId();
    }

    public void update(final Long id, final KotaDTO kotaDTO) {
        final Kota kota = kotaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(kotaDTO, kota);
        kotaRepository.save(kota);
    }

    public void delete(final Long id) {
        kotaRepository.deleteById(id);
    }

    private KotaDTO mapToDTO(final Kota kota, final KotaDTO kotaDTO) {
        kotaDTO.setId(kota.getId());
        kotaDTO.setName(kota.getName());
        return kotaDTO;
    }

    private Kota mapToEntity(final KotaDTO kotaDTO, final Kota kota) {
        kota.setName(kotaDTO.getName());
        return kota;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Kota kota = kotaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Cabang kotaIdCabang = cabangRepository.findFirstByKotaId(kota);
        if (kotaIdCabang != null) {
            referencedWarning.setKey("kota.cabang.kotaId.referenced");
            referencedWarning.addParam(kotaIdCabang.getId());
            return referencedWarning;
        }
        return null;
    }

}
