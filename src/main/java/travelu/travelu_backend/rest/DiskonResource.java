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
import travelu.travelu_backend.model.DiskonDTO;
import travelu.travelu_backend.service.DiskonService;


@RestController
@RequestMapping(value = "/api/diskons", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiskonResource {

    private final DiskonService diskonService;

    public DiskonResource(final DiskonService diskonService) {
        this.diskonService = diskonService;
    }

    @GetMapping
    public ResponseEntity<List<DiskonDTO>> getAllDiskons() {
        return ResponseEntity.ok(diskonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiskonDTO> getDiskon(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(diskonService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDiskon(@RequestBody @Valid final DiskonDTO diskonDTO) {
        final Long createdId = diskonService.create(diskonDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDiskon(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DiskonDTO diskonDTO) {
        diskonService.update(id, diskonDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDiskon(@PathVariable(name = "id") final Long id) {
        diskonService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
