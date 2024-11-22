package travelu.travelu_backend.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelu.travelu_backend.domain.Admin;
import travelu.travelu_backend.domain.Diskon;
import travelu.travelu_backend.domain.InvoicePembayaran;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.domain.Pelanggan;
import travelu.travelu_backend.domain.Pembayaran;
import travelu.travelu_backend.domain.Pemesanan;


public interface PemesananRepository extends JpaRepository<Pemesanan, Long> {

    Pemesanan findFirstByPelangganId(Pelanggan pelanggan);

    Pemesanan findFirstByPembayaranId(Pembayaran pembayaran);

    Pemesanan findFirstByInvoicePembayaranId(InvoicePembayaran invoicePembayaran);

    Pemesanan findFirstByListDiskon(Diskon diskon);

    Pemesanan findFirstByRoleAdmin(Admin admin);

    Pemesanan findFirstByTanggalJadwal(Jadwal jadwal);

    List<Pemesanan> findAllByListDiskon(Diskon diskon);

    List<Pemesanan> findAllByRoleAdmin(Admin admin);

}
