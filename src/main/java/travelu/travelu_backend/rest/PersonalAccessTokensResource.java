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
import travelu.travelu_backend.model.PersonalAccessTokensDTO;
import travelu.travelu_backend.service.PersonalAccessTokensService;


@RestController
@RequestMapping(value = "/api/personalAccessTokenss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonalAccessTokensResource {

    private final PersonalAccessTokensService personalAccessTokensService;

    public PersonalAccessTokensResource(
            final PersonalAccessTokensService personalAccessTokensService) {
        this.personalAccessTokensService = personalAccessTokensService;
    }

    @GetMapping
    public ResponseEntity<List<PersonalAccessTokensDTO>> getAllPersonalAccessTokenss() {
        return ResponseEntity.ok(personalAccessTokensService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalAccessTokensDTO> getPersonalAccessTokens(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(personalAccessTokensService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPersonalAccessTokens(
            @RequestBody @Valid final PersonalAccessTokensDTO personalAccessTokensDTO) {
        final Long createdId = personalAccessTokensService.create(personalAccessTokensDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePersonalAccessTokens(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PersonalAccessTokensDTO personalAccessTokensDTO) {
        personalAccessTokensService.update(id, personalAccessTokensDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePersonalAccessTokens(
            @PathVariable(name = "id") final Long id) {
        personalAccessTokensService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
