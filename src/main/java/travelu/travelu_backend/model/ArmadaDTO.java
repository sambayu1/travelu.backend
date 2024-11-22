package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArmadaDTO {

    private Long id;

    @NotNull
    private String supir;

    @NotNull
    @Size(max = 255)
    private String platNom;

}
