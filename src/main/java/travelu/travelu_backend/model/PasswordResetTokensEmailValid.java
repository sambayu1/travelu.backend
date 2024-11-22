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
import travelu.travelu_backend.service.PasswordResetTokensService;


/**
 * Check that email is present and available when a new PasswordResetTokens is created.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = PasswordResetTokensEmailValid.PasswordResetTokensEmailValidValidator.class
)
public @interface PasswordResetTokensEmailValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PasswordResetTokensEmailValidValidator implements ConstraintValidator<PasswordResetTokensEmailValid, String> {

        private final PasswordResetTokensService passwordResetTokensService;
        private final HttpServletRequest request;

        public PasswordResetTokensEmailValidValidator(
                final PasswordResetTokensService passwordResetTokensService,
                final HttpServletRequest request) {
            this.passwordResetTokensService = passwordResetTokensService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("email");
            if (currentId != null) {
                // only relevant for new objects
                return true;
            }
            String error = null;
            if (value == null) {
                // missing input
                error = "NotNull";
            } else if (passwordResetTokensService.emailExists(value)) {
                error = "Exists.passwordResetTokens.email";
            }
            if (error != null) {
                cvContext.disableDefaultConstraintViolation();
                cvContext.buildConstraintViolationWithTemplate("{" + error + "}")
                        .addConstraintViolation();
                return false;
            }
            return true;
        }

    }

}
