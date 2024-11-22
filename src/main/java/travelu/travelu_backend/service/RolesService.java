package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Roles;
import travelu.travelu_backend.model.RolesDTO;
import travelu.travelu_backend.repos.RolesRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    public RolesService(final RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<RolesDTO> findAll() {
        final List<Roles> roleses = rolesRepository.findAll(Sort.by("id"));
        return roleses.stream()
                .map(roles -> mapToDTO(roles, new RolesDTO()))
                .toList();
    }

    public RolesDTO get(final Integer id) {
        return rolesRepository.findById(id)
                .map(roles -> mapToDTO(roles, new RolesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RolesDTO rolesDTO) {
        final Roles roles = new Roles();
        mapToEntity(rolesDTO, roles);
        return rolesRepository.save(roles).getId();
    }

    public void update(final Integer id, final RolesDTO rolesDTO) {
        final Roles roles = rolesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(rolesDTO, roles);
        rolesRepository.save(roles);
    }

    public void delete(final Integer id) {
        rolesRepository.deleteById(id);
    }

    private RolesDTO mapToDTO(final Roles roles, final RolesDTO rolesDTO) {
        rolesDTO.setId(roles.getId());
        rolesDTO.setSlug(roles.getSlug());
        rolesDTO.setName(roles.getName());
        rolesDTO.setPermissions(roles.getPermissions());
        rolesDTO.setCreatedAt(roles.getCreatedAt());
        rolesDTO.setUpdatedAt(roles.getUpdatedAt());
        return rolesDTO;
    }

    private Roles mapToEntity(final RolesDTO rolesDTO, final Roles roles) {
        roles.setSlug(rolesDTO.getSlug());
        roles.setName(rolesDTO.getName());
        roles.setPermissions(rolesDTO.getPermissions());
        roles.setCreatedAt(rolesDTO.getCreatedAt());
        roles.setUpdatedAt(rolesDTO.getUpdatedAt());
        return roles;
    }

}
