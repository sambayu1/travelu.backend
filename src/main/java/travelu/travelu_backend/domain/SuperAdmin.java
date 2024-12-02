package travelu.travelu_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class SuperAdmin extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id_id", unique = true)
    private Admin adminId;

}
