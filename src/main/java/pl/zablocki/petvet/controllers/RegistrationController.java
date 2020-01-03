package pl.zablocki.petvet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.services.AccountService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping(path = "/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        } else {
            accountService.createUserAccount(accountDto.getEmail(),accountDto.getPassword());
            return new ModelAndView("login", "user", accountDto);
        }
    }

}