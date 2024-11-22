package travelu.travelu_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Jadwal {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer armadaId;

    @Column(nullable = false)
    private String hari;

    @Column(nullable = false)
    private Integer tanggal;

    @Column(nullable = false)
    private String bulan;

    @Column(nullable = false)
    private Integer tahun;

    @Column(nullable = false)
    private LocalDate waktuKeberagkatan;

    @Column(nullable = false)
    private String lokasiKeberangkatan;

    @Column(nullable = false)
    private String tujuan;

    @Column(nullable = false)
    private Integer hargaTiket;

    @OneToMany(mappedBy = "tanggalJadwal")
    private Set<Pemesanan> listPemesanan;

    @ManyToMany(mappedBy = "tanggalJadwal")
    private Set<Pelanggan> pelangganId;

    @ManyToMany(mappedBy = "tanggalJadwal")
    private Set<Admin> roleAdmin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_armada_id", nullable = false, unique = true)
    private Armada listArmada;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
