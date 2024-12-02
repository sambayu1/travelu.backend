package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Csticket;
import travelu.travelu_backend.domain.Pelanggan;
import travelu.travelu_backend.domain.Pemesanan;


public interface CsticketRepository extends JpaRepository<Csticket, Long> {

    Csticket findFirstByPemesananId(Pemesanan pemesanan);

    Csticket findFirstByPelangganId(Pelanggan pelanggan);

}
