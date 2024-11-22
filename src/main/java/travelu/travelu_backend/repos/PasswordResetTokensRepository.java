package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.PasswordResetTokens;


public interface PasswordResetTokensRepository extends JpaRepository<PasswordResetTokens, String> {

    boolean existsByEmailIgnoreCase(String email);

}
