package travelu.travelu_backend.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.DestinasiItinerari;
import travelu.travelu_backend.model.DestinasiItinerariDTO;
import travelu.travelu_backend.repos.DestinasiItinerariRepository;
import travelu.travelu_backend.repos.ItinerariRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
@Transactional
public class DestinasiItinerariService {

    private final DestinasiItinerariRepository destinasiItinerariRepository;
    private final ItinerariRepository itinerariRepository;

    public DestinasiItinerariService(
            final DestinasiItinerariRepository destinasiItinerariRepository,
            final ItinerariRepository itinerariRepository) {
        this.destinasiItinerariRepository = destinasiItinerariRepository;
        this.itinerariRepository = itinerariRepository;
    }

    public List<DestinasiItinerariDTO> findAll() {
        final List<DestinasiItinerari> destinasiItineraris = destinasiItinerariRepository.findAll(Sort.by("id"));
        return destinasiItineraris.stream()
                .map(destinasiItinerari -> mapToDTO(destinasiItinerari, new DestinasiItinerariDTO()))
                .toList();
    }

    public DestinasiItinerariDTO get(final Long id) {
        return destinasiItinerariRepository.findById(id)
                .map(destinasiItinerari -> mapToDTO(destinasiItinerari, new DestinasiItinerariDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DestinasiItinerariDTO destinasiItinerariDTO) {
        final DestinasiItinerari destinasiItinerari = new DestinasiItinerari();
        mapToEntity(destinasiItinerariDTO, destinasiItinerari);
        return destinasiItinerariRepository.save(destinasiItinerari).getId();
    }

    public void update(final Long id, final DestinasiItinerariDTO destinasiItinerariDTO) {
        final DestinasiItinerari destinasiItinerari = destinasiItinerariRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(destinasiItinerariDTO, destinasiItinerari);
        destinasiItinerariRepository.save(destinasiItinerari);
    }

    public void delete(final Long id) {
        final DestinasiItinerari destinasiItinerari = destinasiItinerariRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        itinerariRepository.findAllByListDestinasi(destinasiItinerari)
                .forEach(itinerari -> itinerari.getListDestinasi().remove(destinasiItinerari));
        destinasiItinerariRepository.delete(destinasiItinerari);
    }

    private DestinasiItinerariDTO mapToDTO(final DestinasiItinerari destinasiItinerari,
            final DestinasiItinerariDTO destinasiItinerariDTO) {
        destinasiItinerariDTO.setId(destinasiItinerari.getId());
        destinasiItinerariDTO.setName(destinasiItinerari.getName());
        destinasiItinerariDTO.setImg(destinasiItinerari.getImg());
        destinasiItinerariDTO.setDeskripsi(destinasiItinerari.getDeskripsi());
        destinasiItinerariDTO.setKota(destinasiItinerari.getKota());
        return destinasiItinerariDTO;
    }

    private DestinasiItinerari mapToEntity(final DestinasiItinerariDTO destinasiItinerariDTO,
            final DestinasiItinerari destinasiItinerari) {
        destinasiItinerari.setName(destinasiItinerariDTO.getName());
        destinasiItinerari.setImg(destinasiItinerariDTO.getImg());
        destinasiItinerari.setDeskripsi(destinasiItinerariDTO.getDeskripsi());
        destinasiItinerari.setKota(destinasiItinerariDTO.getKota());
        return destinasiItinerari;
    }

}
