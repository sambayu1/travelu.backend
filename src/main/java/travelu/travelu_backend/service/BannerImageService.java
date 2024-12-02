package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.BannerImage;
import travelu.travelu_backend.model.BannerImageDTO;
import travelu.travelu_backend.repos.BannerImageRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class BannerImageService {

    private final BannerImageRepository bannerImageRepository;

    public BannerImageService(final BannerImageRepository bannerImageRepository) {
        this.bannerImageRepository = bannerImageRepository;
    }

    public List<BannerImageDTO> findAll() {
        final List<BannerImage> bannerImages = bannerImageRepository.findAll(Sort.by("id"));
        return bannerImages.stream()
                .map(bannerImage -> mapToDTO(bannerImage, new BannerImageDTO()))
                .toList();
    }

    public BannerImageDTO get(final Long id) {
        return bannerImageRepository.findById(id)
                .map(bannerImage -> mapToDTO(bannerImage, new BannerImageDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BannerImageDTO bannerImageDTO) {
        final BannerImage bannerImage = new BannerImage();
        mapToEntity(bannerImageDTO, bannerImage);
        return bannerImageRepository.save(bannerImage).getId();
    }

    public void update(final Long id, final BannerImageDTO bannerImageDTO) {
        final BannerImage bannerImage = bannerImageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bannerImageDTO, bannerImage);
        bannerImageRepository.save(bannerImage);
    }

    public void delete(final Long id) {
        bannerImageRepository.deleteById(id);
    }

    private BannerImageDTO mapToDTO(final BannerImage bannerImage,
            final BannerImageDTO bannerImageDTO) {
        bannerImageDTO.setId(bannerImage.getId());
        bannerImageDTO.setName(bannerImage.getName());
        bannerImageDTO.setImg(bannerImage.getImg());
        return bannerImageDTO;
    }

    private BannerImage mapToEntity(final BannerImageDTO bannerImageDTO,
            final BannerImage bannerImage) {
        bannerImage.setName(bannerImageDTO.getName());
        bannerImage.setImg(bannerImageDTO.getImg());
        return bannerImage;
    }

}
