package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
}