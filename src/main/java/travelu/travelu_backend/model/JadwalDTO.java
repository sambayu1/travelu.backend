package travelu.travelu_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JadwalDTO {

    private Long id;

    @NotNull
    private Integer armadaId;

    @NotNull
    @Size(max = 255)
    private String hari;

    @NotNull
    private Integer tanggal;

    @NotNull
    @Size(max = 255)
    private String bulan;

    @NotNull
    private Integer tahun;

    @NotNull
    private LocalDate waktuKeberagkatan;

    @NotNull
    @Size(max = 255)
    private String lokasiKeberangkatan;

    @NotNull
    @Size(max = 255)
    private String tujuan;

    @NotNull
    private Integer hargaTiket;

    @NotNull
    @JadwalListArmadaUnique
    private Long listArmada;

}
