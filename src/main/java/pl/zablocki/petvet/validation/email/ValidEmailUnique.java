package pl.zablocki.petvet.validation.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailUniqueValidator.class)
@Documented
public @interface ValidEmailUnique {
    String message() default "Account with this email already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
