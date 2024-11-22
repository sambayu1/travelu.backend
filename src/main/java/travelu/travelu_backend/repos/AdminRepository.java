package travelu.travelu_backend.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Admin;
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Csticket;
import travelu.travelu_backend.domain.Jadwal;


public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findFirstByListArmada(Armada armada);

    Admin findFirstByCsTicket(Csticket csticket);

    Admin findFirstByTanggalJadwal(Jadwal jadwal);

    List<Admin> findAllByListArmada(Armada armada);

    List<Admin> findAllByCsTicket(Csticket csticket);

    List<Admin> findAllByTanggalJadwal(Jadwal jadwal);

}
