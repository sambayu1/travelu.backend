package travelu.travelu_backend.service;

import java.util.List;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import travelu.travelu_backend.domain.*;
import travelu.travelu_backend.model.PelangganDTO;
import travelu.travelu_backend.model.VerificationCodeGenerator;
import travelu.travelu_backend.repos.CsticketRepository;
import travelu.travelu_backend.repos.PelangganRepository;
import travelu.travelu_backend.repos.PemesananRepository;
import travelu.travelu_backend.repos.UserVerificationRepository;
import travelu.travelu_backend.util.NotFoundException;
import travelu.travelu_backend.util.ReferencedWarning;


@Service
public class PelangganService {

    private final PelangganRepository pelangganRepository;
    private final PemesananRepository pemesananRepository;
    private final CsticketRepository csticketRepository;
    private final UserVerificationRepository UserVerificationRepository;

    public PelangganService(final PelangganRepository pelangganRepository,
            final PemesananRepository pemesananRepository,
            final CsticketRepository csticketRepository,
              final UserVerificationRepository UserVerificationRepository) {
        this.pelangganRepository = pelangganRepository;
        this.pemesananRepository = pemesananRepository;
        this.csticketRepository = csticketRepository;
        this.UserVerificationRepository = UserVerificationRepository;
    }

    public List<PelangganDTO> findAll() {
        final List<Pelanggan> pelanggans = pelangganRepository.findAll(Sort.by("id"));
        return pelanggans.stream()
                .map(pelanggan -> mapToDTO(pelanggan, new PelangganDTO()))
                .toList();
    }

    public PelangganDTO get(final Long id) {
        return pelangganRepository.findById(id)
                .map(pelanggan -> mapToDTO(pelanggan, new PelangganDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PelangganDTO pelangganDTO) {
        final Pelanggan pelanggan = new Pelanggan();
        mapToEntity(pelangganDTO, pelanggan);
        pelanggan.setStatus("UNVERIFIED"); // Set the status to UNVERIFIED
        final Long id = pelangganRepository.save(pelanggan).getId();

        // Generate a verification code
        final String verificationCode = VerificationCodeGenerator.generateVerificationCode();

        // Create a new UserVerification object
        final UserVerification userVerification = new UserVerification();
        userVerification.setEmail(pelanggan.getEmail());
        userVerification.setVerificationCode(verificationCode);

        // Save the UserVerification object to the database
        UserVerificationRepository.save(userVerification);

        // Send the verification email
        sendVerificationEmail(pelanggan, verificationCode);

        return id;
    }

    private void sendVerificationEmail(final Pelanggan pelanggan, final String verificationCode) {
        // Send the verification email
        final String subject = "Verify your email address";
        final String body = "Your verification code is: " + verificationCode + "\n\n"
                + "Please click on the following link to verify your email address: "
                + "http://localhost:8080/verify-email/" + pelanggan.getEmail() + "/" + verificationCode;
        final String from = "no-reply@travelu.com";
        final String to = pelanggan.getEmail();

        // Use JavaMail to send the email
        final Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "8025");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        final Session session = Session.getInstance(props);

        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            // Handle the exception
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    public void update(final Long id, final PelangganDTO pelangganDTO) {
        final Pelanggan pelanggan = pelangganRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pelangganDTO, pelanggan);
        pelangganRepository.save(pelanggan);
    }

    public void delete(final Long id) {
        pelangganRepository.deleteById(id);
    }

    private PelangganDTO mapToDTO(final Pelanggan pelanggan, final PelangganDTO pelangganDTO) {
        pelangganDTO.setId(pelanggan.getId());
        pelangganDTO.setName(pelanggan.getName());
        pelangganDTO.setEmail(pelanggan.getEmail());
        pelangganDTO.setPassword(pelanggan.getPassword());
        pelangganDTO.setRole(pelanggan.getRole());
        pelangganDTO.setNoTelp(pelanggan.getNoTelp());
        pelangganDTO.setStatus(pelanggan.getStatus());
        return pelangganDTO;
    }

    private Pelanggan mapToEntity(final PelangganDTO pelangganDTO, final Pelanggan pelanggan) {
        pelanggan.setName(pelangganDTO.getName());
        pelanggan.setEmail(pelangganDTO.getEmail());
        pelanggan.setPassword(pelangganDTO.getPassword());
        pelanggan.setRole(pelangganDTO.getRole());
        pelanggan.setNoTelp(pelangganDTO.getNoTelp());
        pelanggan.setStatus(pelangganDTO.getStatus());
        return pelanggan;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Pelanggan pelanggan = pelangganRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Pemesanan pelangganIdPemesanan = pemesananRepository.findFirstByPelangganId(pelanggan);
        if (pelangganIdPemesanan != null) {
            referencedWarning.setKey("pelanggan.pemesanan.pelangganId.referenced");
            referencedWarning.addParam(pelangganIdPemesanan.getId());
            return referencedWarning;
        }
        final Csticket pelangganIdCsticket = csticketRepository.findFirstByPelangganId(pelanggan);
        if (pelangganIdCsticket != null) {
            referencedWarning.setKey("pelanggan.csticket.pelangganId.referenced");
            referencedWarning.addParam(pelangganIdCsticket.getId());
            return referencedWarning;
        }
        return null;
    }

}
