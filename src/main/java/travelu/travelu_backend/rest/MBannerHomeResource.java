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
import travelu.travelu_backend.model.MBannerHomeDTO;
import travelu.travelu_backend.service.MBannerHomeService;


@RestController
@RequestMapping(value = "/api/mBannerHomes", produces = MediaType.APPLICATION_JSON_VALUE)
public class MBannerHomeResource {

    private final MBannerHomeService mBannerHomeService;

    public MBannerHomeResource(final MBannerHomeService mBannerHomeService) {
        this.mBannerHomeService = mBannerHomeService;
    }

    @GetMapping
    public ResponseEntity<List<MBannerHomeDTO>> getAllMBannerHomes() {
        return ResponseEntity.ok(mBannerHomeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MBannerHomeDTO> getMBannerHome(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(mBannerHomeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMBannerHome(
            @RequestBody @Valid final MBannerHomeDTO mBannerHomeDTO) {
        final Long createdId = mBannerHomeService.create(mBannerHomeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMBannerHome(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MBannerHomeDTO mBannerHomeDTO) {
        mBannerHomeService.update(id, mBannerHomeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMBannerHome(@PathVariable(name = "id") final Long id) {
        mBannerHomeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
