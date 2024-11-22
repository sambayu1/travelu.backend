package travelu.travelu_backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelu.travelu_backend.model.PasswordResetTokensDTO;
import travelu.travelu_backend.service.PasswordResetTokensService;


@RestController
@RequestMapping(value = "/api/passwordResetTokenss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordResetTokensResource {

    private final PasswordResetTokensService passwordResetTokensService;

    public PasswordResetTokensResource(
            final PasswordResetTokensService passwordResetTokensService) {
        this.passwordResetTokensService = passwordResetTokensService;
    }

    @GetMapping
    public ResponseEntity<List<PasswordResetTokensDTO>> getAllPasswordResetTokenss() {
        return ResponseEntity.ok(passwordResetTokensService.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<PasswordResetTokensDTO> getPasswordResetTokens(
            @PathVariable(name = "email") final String email) {
        return ResponseEntity.ok(passwordResetTokensService.get(email));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createPasswordResetTokens(
            @RequestBody @Valid final PasswordResetTokensDTO passwordResetTokensDTO) {
        final String createdEmail = passwordResetTokensService.create(passwordResetTokensDTO);
        return new ResponseEntity<>('"' + createdEmail + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updatePasswordResetTokens(
            @PathVariable(name = "email") final String email,
            @RequestBody @Valid final PasswordResetTokensDTO passwordResetTokensDTO) {
        passwordResetTokensService.update(email, passwordResetTokensDTO);
        return ResponseEntity.ok('"' + email + '"');
    }

    @DeleteMapping("/{email}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePasswordResetTokens(
            @PathVariable(name = "email") final String email) {
        passwordResetTokensService.delete(email);
        return ResponseEntity.noContent().build();
    }

}
