package travelu.travelu_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Pelanggan {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "longtext")
    private String nama;

    @Column(nullable = false, columnDefinition = "longtext")
    private String email;

    @Column(nullable = false, columnDefinition = "longtext")
    private String noTelp;

    @OneToMany(mappedBy = "pelangganId")
    private Set<Pemesanan> listPemesanan;

    @ManyToMany
    @JoinTable(
            name = "PelangganCekJadwal",
            joinColumns = @JoinColumn(name = "pelangganId"),
            inverseJoinColumns = @JoinColumn(name = "jadwalId")
    )
    private Set<Jadwal> tanggalJadwal;

    @OneToMany(mappedBy = "pelangganId")
    private Set<Csticket> csTicket;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
