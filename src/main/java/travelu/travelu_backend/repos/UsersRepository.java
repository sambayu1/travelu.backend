package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Users;


public interface UsersRepository extends JpaRepository<Users, Long> {
}
