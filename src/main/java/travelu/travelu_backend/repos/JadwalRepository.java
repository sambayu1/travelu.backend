package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Cabang;
import travelu.travelu_backend.domain.Jadwal;


public interface JadwalRepository extends JpaRepository<Jadwal, Long> {

    Jadwal findFirstByArmadaId(Armada armada);

    Jadwal findFirstByAsalCabangId(Cabang cabang);

    Jadwal findFirstByDestinasiCabangId(Cabang cabang);

    boolean existsByArmadaIdId(Long id);

}
