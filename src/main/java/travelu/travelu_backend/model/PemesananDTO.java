package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PemesananDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String ticketCode;

    @NotNull
    @Size(max = 255)
    private String namaCustomer;

    private String diskon;

    @NotNull
    @Size(max = 255)
    private String asal;

    @NotNull
    @Size(max = 255)
    private String tujuan;

    @NotNull
    private LocalDate tglKeberangkatan;

    @NotNull
    @Size(max = 255)
    private String noTempatduduk;

    @NotNull
    @Size(max = 255)
    private String statusPembayaran;

    @NotNull
    private Long pelangganId;

    @NotNull
    private Long pembayaranId;

    @NotNull
    private Long invoicePembayaranId;

    private List<Long> listDiskon;

    private List<Long> roleAdmin;

    @NotNull
    private Long tanggalJadwal;

}
