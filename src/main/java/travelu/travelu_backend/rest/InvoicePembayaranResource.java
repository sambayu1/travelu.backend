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
import travelu.travelu_backend.model.InvoicePembayaranDTO;
import travelu.travelu_backend.service.InvoicePembayaranService;
import travelu.travelu_backend.util.ReferencedException;
import travelu.travelu_backend.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/invoicePembayarans", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoicePembayaranResource {

    private final InvoicePembayaranService invoicePembayaranService;

    public InvoicePembayaranResource(final InvoicePembayaranService invoicePembayaranService) {
        this.invoicePembayaranService = invoicePembayaranService;
    }

    @GetMapping
    public ResponseEntity<List<InvoicePembayaranDTO>> getAllInvoicePembayarans() {
        return ResponseEntity.ok(invoicePembayaranService.findAll());
    }

    @GetMapping("/{noInvoice}")
    public ResponseEntity<InvoicePembayaranDTO> getInvoicePembayaran(
            @PathVariable(name = "noInvoice") final Long noInvoice) {
        return ResponseEntity.ok(invoicePembayaranService.get(noInvoice));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInvoicePembayaran(
            @RequestBody @Valid final InvoicePembayaranDTO invoicePembayaranDTO) {
        final Long createdNoInvoice = invoicePembayaranService.create(invoicePembayaranDTO);
        return new ResponseEntity<>(createdNoInvoice, HttpStatus.CREATED);
    }

    @PutMapping("/{noInvoice}")
    public ResponseEntity<Long> updateInvoicePembayaran(
            @PathVariable(name = "noInvoice") final Long noInvoice,
            @RequestBody @Valid final InvoicePembayaranDTO invoicePembayaranDTO) {
        invoicePembayaranService.update(noInvoice, invoicePembayaranDTO);
        return ResponseEntity.ok(noInvoice);
    }

    @DeleteMapping("/{noInvoice}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInvoicePembayaran(
            @PathVariable(name = "noInvoice") final Long noInvoice) {
        final ReferencedWarning referencedWarning = invoicePembayaranService.getReferencedWarning(noInvoice);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        invoicePembayaranService.delete(noInvoice);
        return ResponseEntity.noContent().build();
    }

}
