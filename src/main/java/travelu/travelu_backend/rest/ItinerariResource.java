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
import travelu.travelu_backend.model.ItinerariDTO;
import travelu.travelu_backend.service.ItinerariService;


@RestController
@RequestMapping(value = "/api/itineraris", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItinerariResource {

    private final ItinerariService itinerariService;

    public ItinerariResource(final ItinerariService itinerariService) {
        this.itinerariService = itinerariService;
    }

    @GetMapping
    public ResponseEntity<List<ItinerariDTO>> getAllItineraris() {
        return ResponseEntity.ok(itinerariService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItinerariDTO> getItinerari(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(itinerariService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createItinerari(
            @RequestBody @Valid final ItinerariDTO itinerariDTO) {
        final Long createdId = itinerariService.create(itinerariDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateItinerari(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ItinerariDTO itinerariDTO) {
        itinerariService.update(id, itinerariDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteItinerari(@PathVariable(name = "id") final Long id) {
        itinerariService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
