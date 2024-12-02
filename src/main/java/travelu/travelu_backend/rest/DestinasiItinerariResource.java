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
import travelu.travelu_backend.model.DestinasiItinerariDTO;
import travelu.travelu_backend.service.DestinasiItinerariService;


@RestController
@RequestMapping(value = "/api/destinasiItineraris", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestinasiItinerariResource {

    private final DestinasiItinerariService destinasiItinerariService;

    public DestinasiItinerariResource(final DestinasiItinerariService destinasiItinerariService) {
        this.destinasiItinerariService = destinasiItinerariService;
    }

    @GetMapping
    public ResponseEntity<List<DestinasiItinerariDTO>> getAllDestinasiItineraris() {
        return ResponseEntity.ok(destinasiItinerariService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinasiItinerariDTO> getDestinasiItinerari(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(destinasiItinerariService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDestinasiItinerari(
            @RequestBody @Valid final DestinasiItinerariDTO destinasiItinerariDTO) {
        final Long createdId = destinasiItinerariService.create(destinasiItinerariDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDestinasiItinerari(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DestinasiItinerariDTO destinasiItinerariDTO) {
        destinasiItinerariService.update(id, destinasiItinerariDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDestinasiItinerari(@PathVariable(name = "id") final Long id) {
        destinasiItinerariService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
