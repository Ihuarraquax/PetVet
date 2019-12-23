package pl.zablocki.petvet.entity.dto;

import pl.zablocki.petvet.validation.email.ValidEmail;
import pl.zablocki.petvet.validation.email.ValidEmailUnique;
import pl.zablocki.petvet.validation.password.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
public class UserDto {

    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    private String matchingPassword;

    @ValidEmailUnique
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
