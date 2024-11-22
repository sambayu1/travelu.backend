package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonalAccessTokensDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tokenableType;

    @NotNull
    private Long tokenableId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 64)
    private String token;

    private String abilities;

    private OffsetDateTime lastUsedAt;

    private OffsetDateTime expiresAt;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
