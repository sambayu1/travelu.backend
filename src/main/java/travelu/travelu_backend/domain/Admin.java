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
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "\"Admin\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Admin {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "longtext")
    private String usersId;

    @ManyToMany(mappedBy = "roleAdmin")
    private Set<Pemesanan> listPemesanan;

    @ManyToMany(mappedBy = "roleAdmin")
    private Set<Diskon> listDiskon;

    @ManyToMany
    @JoinTable(
            name = "AdminCekArmada",
            joinColumns = @JoinColumn(name = "adminId"),
            inverseJoinColumns = @JoinColumn(name = "armadaId")
    )
    private Set<Armada> listArmada;

    @ManyToMany
    @JoinTable(
            name = "AdminCekCsticket",
            joinColumns = @JoinColumn(name = "adminId"),
            inverseJoinColumns = @JoinColumn(name = "csticketId")
    )
    private Set<Csticket> csTicket;

    @ManyToMany
    @JoinTable(
            name = "AdminCekJadwal",
            joinColumns = @JoinColumn(name = "adminId"),
            inverseJoinColumns = @JoinColumn(name = "jadwalId")
    )
    private Set<Jadwal> tanggalJadwal;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
