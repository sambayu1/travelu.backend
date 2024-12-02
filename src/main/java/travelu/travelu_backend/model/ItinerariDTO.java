package travelu.travelu_backend.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItinerariDTO {

    private Long id;

    @Size(max = 255)
    private String title;

    private String deskripsi;

    private List<Long> listDestinasi;

    private List<Long> listJadwal;

}
