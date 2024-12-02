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
import travelu.travelu_backend.model.BannerImageDTO;
import travelu.travelu_backend.service.BannerImageService;


@RestController
@RequestMapping(value = "/api/bannerImages", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerImageResource {

    private final BannerImageService bannerImageService;

    public BannerImageResource(final BannerImageService bannerImageService) {
        this.bannerImageService = bannerImageService;
    }

    @GetMapping
    public ResponseEntity<List<BannerImageDTO>> getAllBannerImages() {
        return ResponseEntity.ok(bannerImageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerImageDTO> getBannerImage(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(bannerImageService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBannerImage(
            @RequestBody @Valid final BannerImageDTO bannerImageDTO) {
        final Long createdId = bannerImageService.create(bannerImageDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBannerImage(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final BannerImageDTO bannerImageDTO) {
        bannerImageService.update(id, bannerImageDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBannerImage(@PathVariable(name = "id") final Long id) {
        bannerImageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
