package travelu.travelu_backend.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.DestinasiItinerari;
import travelu.travelu_backend.domain.Itinerari;
import travelu.travelu_backend.domain.Jadwal;


public interface ItinerariRepository extends JpaRepository<Itinerari, Long> {

    Itinerari findFirstByListDestinasi(DestinasiItinerari destinasiItinerari);

    Itinerari findFirstByListJadwal(Jadwal jadwal);

    List<Itinerari> findAllByListDestinasi(DestinasiItinerari destinasiItinerari);

    List<Itinerari> findAllByListJadwal(Jadwal jadwal);

}
