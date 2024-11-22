package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PembayaranDTO {

    private Long id;

    @Size(max = 255)
    private String metode;

    private Double harga;

    @NotNull
    @PembayaranInvoicePembayaranUnique
    private Long invoicePembayaran;

}
