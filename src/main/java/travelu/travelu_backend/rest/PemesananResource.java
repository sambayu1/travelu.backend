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
import travelu.travelu_backend.model.PemesananDTO;
import travelu.travelu_backend.service.PemesananService;
import travelu.travelu_backend.util.ReferencedException;
import travelu.travelu_backend.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/pemesanans", produces = MediaType.APPLICATION_JSON_VALUE)
public class PemesananResource {

    private final PemesananService pemesananService;

    public PemesananResource(final PemesananService pemesananService) {
        this.pemesananService = pemesananService;
    }

    @GetMapping
    public ResponseEntity<List<PemesananDTO>> getAllPemesanans() {
        return ResponseEntity.ok(pemesananService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PemesananDTO> getPemesanan(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(pemesananService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPemesanan(
            @RequestBody @Valid final PemesananDTO pemesananDTO) {
        final Long createdId = pemesananService.create(pemesananDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePemesanan(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PemesananDTO pemesananDTO) {
        pemesananService.update(id, pemesananDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePemesanan(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = pemesananService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        pemesananService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
