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
import travelu.travelu_backend.model.RolesDTO;
import travelu.travelu_backend.service.RolesService;


@RestController
@RequestMapping(value = "/api/roless", produces = MediaType.APPLICATION_JSON_VALUE)
public class RolesResource {

    private final RolesService rolesService;

    public RolesResource(final RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping
    public ResponseEntity<List<RolesDTO>> getAllRoless() {
        return ResponseEntity.ok(rolesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolesDTO> getRoles(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(rolesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRoles(@RequestBody @Valid final RolesDTO rolesDTO) {
        final Integer createdId = rolesService.create(rolesDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateRoles(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final RolesDTO rolesDTO) {
        rolesService.update(id, rolesDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRoles(@PathVariable(name = "id") final Integer id) {
        rolesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
