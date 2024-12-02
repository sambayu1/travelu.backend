package travelu.travelu_backend.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BannerImageDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String img;

}
