package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PelangganDTO {

    private Long id;

    @NotNull
    private String nama;

    @NotNull
    private String email;

    @NotNull
    private String noTelp;

    private List<Long> tanggalJadwal;

}
