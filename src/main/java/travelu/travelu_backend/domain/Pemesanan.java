package travelu.travelu_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Pemesanan {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ticketCode;

    @Column(nullable = false)
    private String namaCustomer;

    @Column(columnDefinition = "longtext")
    private String diskon;

    @Column(nullable = false)
    private String asal;

    @Column(nullable = false)
    private String tujuan;

    @Column(nullable = false)
    private LocalDate tglKeberangkatan;

    @Column(nullable = false)
    private String noTempatduduk;

    @Column(nullable = false)
    private String statusPembayaran;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelanggan_id_id", nullable = false)
    private Pelanggan pelangganId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pembayaran_id_id", nullable = false)
    private Pembayaran pembayaranId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_pembayaran_id_id", nullable = false)
    private InvoicePembayaran invoicePembayaranId;

    @ManyToMany
    @JoinTable(
            name = "DiskonPemesanan",
            joinColumns = @JoinColumn(name = "pemesananId"),
            inverseJoinColumns = @JoinColumn(name = "diskonId")
    )
    private Set<Diskon> listDiskon;

    @ManyToMany
    @JoinTable(
            name = "AdminControlPemesanan",
            joinColumns = @JoinColumn(name = "pemesananId"),
            inverseJoinColumns = @JoinColumn(name = "adminId")
    )
    private Set<Admin> roleAdmin;

    @OneToMany(mappedBy = "listPemesanan")
    private Set<Csticket> csTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tanggal_jadwal_id", nullable = false)
    private Jadwal tanggalJadwal;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
