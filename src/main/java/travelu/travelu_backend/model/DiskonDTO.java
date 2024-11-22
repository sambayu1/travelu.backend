package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DiskonDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nama;

    private Integer harga;

    private Integer percent;

    @NotNull
    @Size(max = 255)
    private String code;

    private List<Long> roleAdmin;

}
