package travelu.travelu_backend.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.InvoicePembayaran;
import travelu.travelu_backend.domain.Pembayaran;
import travelu.travelu_backend.domain.Pemesanan;
import travelu.travelu_backend.model.InvoicePembayaranDTO;
import travelu.travelu_backend.repos.InvoicePembayaranRepository;
import travelu.travelu_backend.repos.PembayaranRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
public class InvoicePembayaranService {

    private final InvoicePembayaranRepository invoicePembayaranRepository;
    private final PemesananRepository pemesananRepository;
    private final PembayaranRepository pembayaranRepository;

    public InvoicePembayaranService(final InvoicePembayaranRepository invoicePembayaranRepository,
            final PemesananRepository pemesananRepository,
            final PembayaranRepository pembayaranRepository) {
        this.invoicePembayaranRepository = invoicePembayaranRepository;
        this.pemesananRepository = pemesananRepository;
        this.pembayaranRepository = pembayaranRepository;
    }

    public List<InvoicePembayaranDTO> findAll() {
        final List<InvoicePembayaran> invoicePembayarans = invoicePembayaranRepository.findAll(Sort.by("noInvoice"));
        return invoicePembayarans.stream()
                .map(invoicePembayaran -> mapToDTO(invoicePembayaran, new InvoicePembayaranDTO()))
                .toList();
    }

    public InvoicePembayaranDTO get(final Long noInvoice) {
        return invoicePembayaranRepository.findById(noInvoice)
                .map(invoicePembayaran -> mapToDTO(invoicePembayaran, new InvoicePembayaranDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InvoicePembayaranDTO invoicePembayaranDTO) {
        final InvoicePembayaran invoicePembayaran = new InvoicePembayaran();
        mapToEntity(invoicePembayaranDTO, invoicePembayaran);
        return invoicePembayaranRepository.save(invoicePembayaran).getNoInvoice();
    }

    public void update(final Long noInvoice, final InvoicePembayaranDTO invoicePembayaranDTO) {
        final InvoicePembayaran invoicePembayaran = invoicePembayaranRepository.findById(noInvoice)
                .orElseThrow(NotFoundException::new);
        mapToEntity(invoicePembayaranDTO, invoicePembayaran);
        invoicePembayaranRepository.save(invoicePembayaran);
    }

    public void delete(final Long noInvoice) {
        invoicePembayaranRepository.deleteById(noInvoice);
    }

    private InvoicePembayaranDTO mapToDTO(final InvoicePembayaran invoicePembayaran,
            final InvoicePembayaranDTO invoicePembayaranDTO) {
        invoicePembayaranDTO.setNoInvoice(invoicePembayaran.getNoInvoice());
        invoicePembayaranDTO.setTicketCode(invoicePembayaran.getTicketCode());
        invoicePembayaranDTO.setStatus(invoicePembayaran.getStatus());
        invoicePembayaranDTO.setHarga(invoicePembayaran.getHarga());
        return invoicePembayaranDTO;
    }

    private InvoicePembayaran mapToEntity(final InvoicePembayaranDTO invoicePembayaranDTO,
            final InvoicePembayaran invoicePembayaran) {
        invoicePembayaran.setTicketCode(invoicePembayaranDTO.getTicketCode());
        invoicePembayaran.setStatus(invoicePembayaranDTO.getStatus());
        invoicePembayaran.setHarga(invoicePembayaranDTO.getHarga());
        return invoicePembayaran;
    }

    public ReferencedWarning getReferencedWarning(final Long noInvoice) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final InvoicePembayaran invoicePembayaran = invoicePembayaranRepository.findById(noInvoice)
                .orElseThrow(NotFoundException::new);
        final Pemesanan invoicePembayaranIdPemesanan = pemesananRepository.findFirstByInvoicePembayaranId(invoicePembayaran);
        if (invoicePembayaranIdPemesanan != null) {
            referencedWarning.setKey("invoicePembayaran.pemesanan.invoicePembayaranId.referenced");
            referencedWarning.addParam(invoicePembayaranIdPemesanan.getId());
            return referencedWarning;
        }
        final Pembayaran invoicePembayaranPembayaran = pembayaranRepository.findFirstByInvoicePembayaran(invoicePembayaran);
        if (invoicePembayaranPembayaran != null) {
            referencedWarning.setKey("invoicePembayaran.pembayaran.invoicePembayaran.referenced");
            referencedWarning.addParam(invoicePembayaranPembayaran.getId());
            return referencedWarning;
        }
        return null;
    }

}
