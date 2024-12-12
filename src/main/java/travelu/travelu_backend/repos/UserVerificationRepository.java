package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.UserVerification;


public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
    UserVerification findByEmail(String email);
}

