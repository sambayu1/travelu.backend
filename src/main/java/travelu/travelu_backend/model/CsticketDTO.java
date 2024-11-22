package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CsticketDTO {

    private Long id;

    @NotNull
    private Integer userId;

    private Integer orderId;

    @NotNull
    private Integer rating;

    @NotNull
    private String content;

    @NotNull
    private Integer status;

    @NotNull
    private Long listPemesanan;

    @NotNull
    private Long pelangganId;

}
