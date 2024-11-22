package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PasswordResetTokensDTO {

    @Size(max = 255)
    @PasswordResetTokensEmailValid
    private String email;

    @NotNull
    @Size(max = 255)
    private String token;

    private OffsetDateTime createdAt;

}
