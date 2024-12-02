package travelu.travelu_backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.BannerImage;


public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {
}
