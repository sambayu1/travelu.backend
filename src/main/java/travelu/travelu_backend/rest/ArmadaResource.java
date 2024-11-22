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
import travelu.travelu_backend.model.ArmadaDTO;
import travelu.travelu_backend.service.ArmadaService;
import travelu.travelu_backend.util.ReferencedException;
import travelu.travelu_backend.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/armadas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArmadaResource {

    private final ArmadaService armadaService;

    public ArmadaResource(final ArmadaService armadaService) {
        this.armadaService = armadaService;
    }

    @GetMapping
    public ResponseEntity<List<ArmadaDTO>> getAllArmadas() {
        return ResponseEntity.ok(armadaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArmadaDTO> getArmada(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(armadaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createArmada(@RequestBody @Valid final ArmadaDTO armadaDTO) {
        final Long createdId = armadaService.create(armadaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateArmada(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ArmadaDTO armadaDTO) {
        armadaService.update(id, armadaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteArmada(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = armadaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        armadaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
