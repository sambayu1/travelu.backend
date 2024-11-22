package travelu.travelu_backend.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.domain.Pelanggan;


public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {

    Pelanggan findFirstByTanggalJadwal(Jadwal jadwal);

    List<Pelanggan> findAllByTanggalJadwal(Jadwal jadwal);

}
