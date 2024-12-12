package travelu.travelu_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.UserVerification;
import travelu.travelu_backend.repos.UserVerificationRepository;


@Service
public class VerificationService {

    @Autowired
    private UserVerificationRepository verificationRepository;

    public boolean verifyCode(String email, String code) {
        UserVerification userVerification = verificationRepository.findByEmail(email);
        if (userVerification != null && userVerification.getVerificationCode().equals(code)) {
            // Verification successful
            return true;
        }
        // Verification failed
        return false;
    }
}

