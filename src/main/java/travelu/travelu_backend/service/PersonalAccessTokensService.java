package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.PersonalAccessTokens;
import travelu.travelu_backend.model.PersonalAccessTokensDTO;
import travelu.travelu_backend.repos.PersonalAccessTokensRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class PersonalAccessTokensService {

    private final PersonalAccessTokensRepository personalAccessTokensRepository;

    public PersonalAccessTokensService(
            final PersonalAccessTokensRepository personalAccessTokensRepository) {
        this.personalAccessTokensRepository = personalAccessTokensRepository;
    }

    public List<PersonalAccessTokensDTO> findAll() {
        final List<PersonalAccessTokens> personalAccessTokenses = personalAccessTokensRepository.findAll(Sort.by("id"));
        return personalAccessTokenses.stream()
                .map(personalAccessTokens -> mapToDTO(personalAccessTokens, new PersonalAccessTokensDTO()))
                .toList();
    }

    public PersonalAccessTokensDTO get(final Long id) {
        return personalAccessTokensRepository.findById(id)
                .map(personalAccessTokens -> mapToDTO(personalAccessTokens, new PersonalAccessTokensDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonalAccessTokensDTO personalAccessTokensDTO) {
        final PersonalAccessTokens personalAccessTokens = new PersonalAccessTokens();
        mapToEntity(personalAccessTokensDTO, personalAccessTokens);
        return personalAccessTokensRepository.save(personalAccessTokens).getId();
    }

    public void update(final Long id, final PersonalAccessTokensDTO personalAccessTokensDTO) {
        final PersonalAccessTokens personalAccessTokens = personalAccessTokensRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(personalAccessTokensDTO, personalAccessTokens);
        personalAccessTokensRepository.save(personalAccessTokens);
    }

    public void delete(final Long id) {
        personalAccessTokensRepository.deleteById(id);
    }

    private PersonalAccessTokensDTO mapToDTO(final PersonalAccessTokens personalAccessTokens,
            final PersonalAccessTokensDTO personalAccessTokensDTO) {
        personalAccessTokensDTO.setId(personalAccessTokens.getId());
        personalAccessTokensDTO.setTokenableType(personalAccessTokens.getTokenableType());
        personalAccessTokensDTO.setTokenableId(personalAccessTokens.getTokenableId());
        personalAccessTokensDTO.setName(personalAccessTokens.getName());
        personalAccessTokensDTO.setToken(personalAccessTokens.getToken());
        personalAccessTokensDTO.setAbilities(personalAccessTokens.getAbilities());
        personalAccessTokensDTO.setLastUsedAt(personalAccessTokens.getLastUsedAt());
        personalAccessTokensDTO.setExpiresAt(personalAccessTokens.getExpiresAt());
        personalAccessTokensDTO.setCreatedAt(personalAccessTokens.getCreatedAt());
        personalAccessTokensDTO.setUpdatedAt(personalAccessTokens.getUpdatedAt());
        return personalAccessTokensDTO;
    }

    private PersonalAccessTokens mapToEntity(final PersonalAccessTokensDTO personalAccessTokensDTO,
            final PersonalAccessTokens personalAccessTokens) {
        personalAccessTokens.setTokenableType(personalAccessTokensDTO.getTokenableType());
        personalAccessTokens.setTokenableId(personalAccessTokensDTO.getTokenableId());
        personalAccessTokens.setName(personalAccessTokensDTO.getName());
        personalAccessTokens.setToken(personalAccessTokensDTO.getToken());
        personalAccessTokens.setAbilities(personalAccessTokensDTO.getAbilities());
        personalAccessTokens.setLastUsedAt(personalAccessTokensDTO.getLastUsedAt());
        personalAccessTokens.setExpiresAt(personalAccessTokensDTO.getExpiresAt());
        personalAccessTokens.setCreatedAt(personalAccessTokensDTO.getCreatedAt());
        personalAccessTokens.setUpdatedAt(personalAccessTokensDTO.getUpdatedAt());
        return personalAccessTokens;
    }

}
