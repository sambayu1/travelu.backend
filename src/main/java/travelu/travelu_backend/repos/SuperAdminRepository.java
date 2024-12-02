package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Admin;
import travelu.travelu_backend.domain.SuperAdmin;


public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

    SuperAdmin findFirstByAdminId(Admin admin);

}
