package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.MBannerHome;
import travelu.travelu_backend.model.MBannerHomeDTO;
import travelu.travelu_backend.repos.MBannerHomeRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class MBannerHomeService {

    private final MBannerHomeRepository mBannerHomeRepository;

    public MBannerHomeService(final MBannerHomeRepository mBannerHomeRepository) {
        this.mBannerHomeRepository = mBannerHomeRepository;
    }

    public List<MBannerHomeDTO> findAll() {
        final List<MBannerHome> mBannerHomes = mBannerHomeRepository.findAll(Sort.by("id"));
        return mBannerHomes.stream()
                .map(mBannerHome -> mapToDTO(mBannerHome, new MBannerHomeDTO()))
                .toList();
    }

    public MBannerHomeDTO get(final Long id) {
        return mBannerHomeRepository.findById(id)
                .map(mBannerHome -> mapToDTO(mBannerHome, new MBannerHomeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MBannerHomeDTO mBannerHomeDTO) {
        final MBannerHome mBannerHome = new MBannerHome();
        mapToEntity(mBannerHomeDTO, mBannerHome);
        return mBannerHomeRepository.save(mBannerHome).getId();
    }

    public void update(final Long id, final MBannerHomeDTO mBannerHomeDTO) {
        final MBannerHome mBannerHome = mBannerHomeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(mBannerHomeDTO, mBannerHome);
        mBannerHomeRepository.save(mBannerHome);
    }

    public void delete(final Long id) {
        mBannerHomeRepository.deleteById(id);
    }

    private MBannerHomeDTO mapToDTO(final MBannerHome mBannerHome,
            final MBannerHomeDTO mBannerHomeDTO) {
        mBannerHomeDTO.setId(mBannerHome.getId());
        mBannerHomeDTO.setName(mBannerHome.getName());
        mBannerHomeDTO.setStatus(mBannerHome.getStatus());
        mBannerHomeDTO.setImg(mBannerHome.getImg());
        mBannerHomeDTO.setCreatedAt(mBannerHome.getCreatedAt());
        mBannerHomeDTO.setUpdatedAt(mBannerHome.getUpdatedAt());
        return mBannerHomeDTO;
    }

    private MBannerHome mapToEntity(final MBannerHomeDTO mBannerHomeDTO,
            final MBannerHome mBannerHome) {
        mBannerHome.setName(mBannerHomeDTO.getName());
        mBannerHome.setStatus(mBannerHomeDTO.getStatus());
        mBannerHome.setImg(mBannerHomeDTO.getImg());
        mBannerHome.setCreatedAt(mBannerHomeDTO.getCreatedAt());
        mBannerHome.setUpdatedAt(mBannerHomeDTO.getUpdatedAt());
        return mBannerHome;
    }

}
