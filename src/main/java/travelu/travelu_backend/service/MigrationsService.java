package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.Migrations;
import travelu.travelu_backend.model.MigrationsDTO;
import travelu.travelu_backend.repos.MigrationsRepository;
import travelu.travelu_backend.util.NotFoundException;


@Service
public class MigrationsService {

    private final MigrationsRepository migrationsRepository;

    public MigrationsService(final MigrationsRepository migrationsRepository) {
        this.migrationsRepository = migrationsRepository;
    }

    public List<MigrationsDTO> findAll() {
        final List<Migrations> migrationses = migrationsRepository.findAll(Sort.by("id"));
        return migrationses.stream()
                .map(migrations -> mapToDTO(migrations, new MigrationsDTO()))
                .toList();
    }

    public MigrationsDTO get(final Integer id) {
        return migrationsRepository.findById(id)
                .map(migrations -> mapToDTO(migrations, new MigrationsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MigrationsDTO migrationsDTO) {
        final Migrations migrations = new Migrations();
        mapToEntity(migrationsDTO, migrations);
        return migrationsRepository.save(migrations).getId();
    }

    public void update(final Integer id, final MigrationsDTO migrationsDTO) {
        final Migrations migrations = migrationsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(migrationsDTO, migrations);
        migrationsRepository.save(migrations);
    }

    public void delete(final Integer id) {
        migrationsRepository.deleteById(id);
    }

    private MigrationsDTO mapToDTO(final Migrations migrations, final MigrationsDTO migrationsDTO) {
        migrationsDTO.setId(migrations.getId());
        migrationsDTO.setMigration(migrations.getMigration());
        migrationsDTO.setBatch(migrations.getBatch());
        return migrationsDTO;
    }

    private Migrations mapToEntity(final MigrationsDTO migrationsDTO, final Migrations migrations) {
        migrations.setMigration(migrationsDTO.getMigration());
        migrations.setBatch(migrationsDTO.getBatch());
        return migrations;
    }

}
