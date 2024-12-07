package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Cabang;
import travelu.travelu_backend.domain.Kota;


public interface CabangRepository extends JpaRepository<Cabang, Long> {

    Cabang findFirstByKotaId(Kota kota);

}
