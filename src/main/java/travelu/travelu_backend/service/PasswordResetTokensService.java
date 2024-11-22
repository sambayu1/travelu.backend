package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.PasswordResetTokens;
import travelu.travelu_backend.model.PasswordResetTokensDTO;
import travelu.travelu_backend.repos.PasswordResetTokensRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class PasswordResetTokensService {

    private final PasswordResetTokensRepository passwordResetTokensRepository;

    public PasswordResetTokensService(
            final PasswordResetTokensRepository passwordResetTokensRepository) {
        this.passwordResetTokensRepository = passwordResetTokensRepository;
    }

    public List<PasswordResetTokensDTO> findAll() {
        final List<PasswordResetTokens> passwordResetTokenses = passwordResetTokensRepository.findAll(Sort.by("email"));
        return passwordResetTokenses.stream()
                .map(passwordResetTokens -> mapToDTO(passwordResetTokens, new PasswordResetTokensDTO()))
                .toList();
    }

    public PasswordResetTokensDTO get(final String email) {
        return passwordResetTokensRepository.findById(email)
                .map(passwordResetTokens -> mapToDTO(passwordResetTokens, new PasswordResetTokensDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final PasswordResetTokensDTO passwordResetTokensDTO) {
        final PasswordResetTokens passwordResetTokens = new PasswordResetTokens();
        mapToEntity(passwordResetTokensDTO, passwordResetTokens);
        passwordResetTokens.setEmail(passwordResetTokensDTO.getEmail());
        return passwordResetTokensRepository.save(passwordResetTokens).getEmail();
    }

    public void update(final String email, final PasswordResetTokensDTO passwordResetTokensDTO) {
        final PasswordResetTokens passwordResetTokens = passwordResetTokensRepository.findById(email)
                .orElseThrow(NotFoundException::new);
        mapToEntity(passwordResetTokensDTO, passwordResetTokens);
        passwordResetTokensRepository.save(passwordResetTokens);
    }

    public void delete(final String email) {
        passwordResetTokensRepository.deleteById(email);
    }

    private PasswordResetTokensDTO mapToDTO(final PasswordResetTokens passwordResetTokens,
            final PasswordResetTokensDTO passwordResetTokensDTO) {
        passwordResetTokensDTO.setEmail(passwordResetTokens.getEmail());
        passwordResetTokensDTO.setToken(passwordResetTokens.getToken());
        passwordResetTokensDTO.setCreatedAt(passwordResetTokens.getCreatedAt());
        return passwordResetTokensDTO;
    }

    private PasswordResetTokens mapToEntity(final PasswordResetTokensDTO passwordResetTokensDTO,
            final PasswordResetTokens passwordResetTokens) {
        passwordResetTokens.setToken(passwordResetTokensDTO.getToken());
        passwordResetTokens.setCreatedAt(passwordResetTokensDTO.getCreatedAt());
        return passwordResetTokens;
    }

    public boolean emailExists(final String email) {
        return passwordResetTokensRepository.existsByEmailIgnoreCase(email);
    }

}
