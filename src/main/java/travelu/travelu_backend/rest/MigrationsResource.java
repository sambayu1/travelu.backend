package travelu.travelu_backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelu.travelu_backend.model.MigrationsDTO;
import travelu.travelu_backend.service.MigrationsService;


@RestController
@RequestMapping(value = "/api/migrationss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MigrationsResource {

    private final MigrationsService migrationsService;

    public MigrationsResource(final MigrationsService migrationsService) {
        this.migrationsService = migrationsService;
    }

    @GetMapping
    public ResponseEntity<List<MigrationsDTO>> getAllMigrationss() {
        return ResponseEntity.ok(migrationsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MigrationsDTO> getMigrations(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(migrationsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMigrations(
            @RequestBody @Valid final MigrationsDTO migrationsDTO) {
        final Integer createdId = migrationsService.create(migrationsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMigrations(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MigrationsDTO migrationsDTO) {
        migrationsService.update(id, migrationsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMigrations(@PathVariable(name = "id") final Integer id) {
        migrationsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
