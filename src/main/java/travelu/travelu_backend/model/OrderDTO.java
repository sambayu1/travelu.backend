package travelu.travelu_backend.model;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
