package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
