package pl.zablocki.petvet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.Vet;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.model.Credentials;
import pl.zablocki.petvet.services.AccountService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/user")
    public String showUserPage() {
        return "userPage";
    }

    @GetMapping(path = "/user/edit/credentials")
    public String showUserEditPage(Model model, Principal principal) {

        if (accountService.isOwner(principal.getName())) {
            Owner owner = accountService.getOwnerByEmail(principal.getName()).get();
            if (owner.getCredentials() != null) {
                model.addAttribute("credentials", owner.getCredentials());
            } else {
                model.addAttribute("credentials", new Credentials());
            }
        } else {
            Vet vet = accountService.getVetByEmail(principal.getName()).get();
            if (vet.getCredentials() != null) {
                model.addAttribute("credentials", vet.getCredentials());
            } else {
                model.addAttribute("credentials", new Credentials());
            }
        }
        return "userEditCredentials";
    }

    @GetMapping(path = "/user/edit/password")
    public String showPasswordEdit(Model model, Principal principal) {

        if (accountService.isOwner(principal.getName())) {
            Owner owner = accountService.getOwnerByEmail(principal.getName()).get();
            model.addAttribute("userDto", accountService.convertToDto(owner.getUser()));
        } else {
            Vet vet = accountService.getVetByEmail(principal.getName()).get();
            model.addAttribute("userDto", accountService.convertToDto(vet.getUser()));
        }
        return "userEditPassword";
    }

    @PostMapping(path = "/user/edit/credentials")
    public ModelAndView processCredentialsEdit(@ModelAttribute Credentials credentials, BindingResult result, Principal principal) {


        if (result.hasErrors()) {
            return new ModelAndView("userEditCredentials", "credentials", credentials);
        }

        accountService.setCredentials(principal.getName(), credentials);
        return new ModelAndView("redirect:/user");
    }

    @PostMapping(path = "/user/edit/password")
    public ModelAndView processPasswordEdit(@ModelAttribute UserDto userDto, BindingResult result) {


        if (result.hasErrors()) {
            return new ModelAndView("userEditPassword", "userDto", userDto);
        }
        accountService.updateUser(userDto);
        return new ModelAndView("redirect:/user");
    }
}
