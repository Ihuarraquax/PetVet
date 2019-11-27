package pl.zablocki.petvet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import pl.zablocki.petvet.entity.Authority;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.UserDto;
import pl.zablocki.petvet.services.AuthorityService;
import pl.zablocki.petvet.services.UserService;
import pl.zablocki.petvet.validation.email.EmailExistsException;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RegistrationController {

    @Autowired
    MessageSource messageSource;
    UserService userService;
    AuthorityService authorityService;

    @Autowired
    public RegistrationController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @GetMapping(path = "/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }


    @PostMapping(path = "/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto accountDto,
            BindingResult result,
            WebRequest request,
            Errors errors) {

        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        } else {
            return new ModelAndView("login", "user", accountDto);
        }
    }

    private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setEmail(accountDto.getEmail());
        authorityService.addAuthority(authority);

        try {
            registered = userService.addNewUserFromDto(accountDto);
        } catch (EmailExistsException e) {

        }
        return registered;
    }
}

