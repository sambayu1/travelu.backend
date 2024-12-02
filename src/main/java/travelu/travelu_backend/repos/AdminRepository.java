package travelu.travelu_backend.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Admin;
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Csticket;
import travelu.travelu_backend.domain.Diskon;
import travelu.travelu_backend.domain.Jadwal;


public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findFirstByListDiskon(Diskon diskon);

    Admin findFirstByListArmada(Armada armada);

    Admin findFirstByListComplain(Csticket csticket);

    Admin findFirstByListJadwal(Jadwal jadwal);

    List<Admin> findAllByListDiskon(Diskon diskon);

    List<Admin> findAllByListArmada(Armada armada);

    List<Admin> findAllByListComplain(Csticket csticket);

    List<Admin> findAllByListJadwal(Jadwal jadwal);

}
