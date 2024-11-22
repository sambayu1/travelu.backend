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
import travelu.travelu_backend.model.CsticketDTO;
import travelu.travelu_backend.service.CsticketService;


@RestController
@RequestMapping(value = "/api/cstickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class CsticketResource {

    private final CsticketService csticketService;

    public CsticketResource(final CsticketService csticketService) {
        this.csticketService = csticketService;
    }

    @GetMapping
    public ResponseEntity<List<CsticketDTO>> getAllCstickets() {
        return ResponseEntity.ok(csticketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CsticketDTO> getCsticket(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(csticketService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCsticket(@RequestBody @Valid final CsticketDTO csticketDTO) {
        final Long createdId = csticketService.create(csticketDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCsticket(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CsticketDTO csticketDTO) {
        csticketService.update(id, csticketDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCsticket(@PathVariable(name = "id") final Long id) {
        csticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
