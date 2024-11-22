package travelu.travelu_backend.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Admin;
import travelu.travelu_backend.domain.Diskon;


public interface DiskonRepository extends JpaRepository<Diskon, Long> {

    Diskon findFirstByRoleAdmin(Admin admin);

    List<Diskon> findAllByRoleAdmin(Admin admin);

}
