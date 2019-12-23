package pl.zablocki.petvet.validation.password;

import pl.zablocki.petvet.entity.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
   public void initialize(PasswordMatches constraint) {
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      UserDto user = (UserDto) obj;
      return user.getPassword().equals(user.getMatchingPassword());
   }
}
