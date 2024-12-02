package travelu.travelu_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "\"Admin\"")
@Getter
@Setter
public class Admin extends User {

    @ManyToMany
    @JoinTable(
            name = "AdminCekDiskon",
            joinColumns = @JoinColumn(name = "adminId"),
            inverseJoinColumns = @JoinColumn(name = "diskonId")
    )
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
    private Set<Csticket> listComplain;

    @ManyToMany
    @JoinTable(
            name = "AdminCekJadwal",
            joinColumns = @JoinColumn(name = "adminId"),
            inverseJoinColumns = @JoinColumn(name = "jadwalId")
    )
    private Set<Jadwal> listJadwal;

}
