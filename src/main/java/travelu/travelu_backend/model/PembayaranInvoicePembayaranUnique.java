package travelu.travelu_backend.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;
import travelu.travelu_backend.service.PembayaranService;


/**
 * Validate that the noInvoice value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = PembayaranInvoicePembayaranUnique.PembayaranInvoicePembayaranUniqueValidator.class
)
public @interface PembayaranInvoicePembayaranUnique {

    String message() default "{Exists.pembayaran.invoice-pembayaran}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PembayaranInvoicePembayaranUniqueValidator implements ConstraintValidator<PembayaranInvoicePembayaranUnique, Long> {

        private final PembayaranService pembayaranService;
        private final HttpServletRequest request;

        public PembayaranInvoicePembayaranUniqueValidator(final PembayaranService pembayaranService,
                final HttpServletRequest request) {
            this.pembayaranService = pembayaranService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(pembayaranService.get(Long.parseLong(currentId)).getInvoicePembayaran())) {
                // value hasn't changed
                return true;
            }
            return !pembayaranService.invoicePembayaranExists(value);
        }

    }

}
