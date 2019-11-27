package pl.zablocki.petvet.validation.email;

import org.springframework.beans.factory.annotation.Autowired;
import pl.zablocki.petvet.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<ValidEmailUnique, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isInRepo(value);
    }

    private boolean isInRepo(String email) {

        return !userRepository.findByEmail(email).isPresent();

    }
}