package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Users;
import travelu.travelu_backend.model.UsersDTO;
import travelu.travelu_backend.repos.UsersRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userses = usersRepository.findAll(Sort.by("id"));
        return userses.stream()
                .map(users -> mapToDTO(users, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final Long id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        return usersRepository.save(users).getId();
    }

    public void update(final Long id, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final Long id) {
        usersRepository.deleteById(id);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setName(users.getName());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setEmailVerifiedAt(users.getEmailVerifiedAt());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setRememberToken(users.getRememberToken());
        usersDTO.setCreatedAt(users.getCreatedAt());
        usersDTO.setUpdatedAt(users.getUpdatedAt());
        usersDTO.setPermissions(users.getPermissions());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setName(usersDTO.getName());
        users.setEmail(usersDTO.getEmail());
        users.setEmailVerifiedAt(usersDTO.getEmailVerifiedAt());
        users.setPassword(usersDTO.getPassword());
        users.setRememberToken(usersDTO.getRememberToken());
        users.setCreatedAt(usersDTO.getCreatedAt());
        users.setUpdatedAt(usersDTO.getUpdatedAt());
        users.setPermissions(usersDTO.getPermissions());
        return users;
    }

}
