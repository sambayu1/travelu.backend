package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RolesDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String slug;

    @NotNull
    @Size(max = 255)
    private String name;

    private String permissions;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
