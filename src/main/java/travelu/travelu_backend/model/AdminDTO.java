package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminDTO {

    private Long id;

    @NotNull
    private String usersId;

    private List<Long> listArmada;

    private List<Long> csTicket;

    private List<Long> tanggalJadwal;

}
