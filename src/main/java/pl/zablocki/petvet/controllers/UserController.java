package pl.zablocki.petvet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.services.AccountService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private AccountService accountService;

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/user")
    public String showUserPage() {
        return "userPage";
    }

    @GetMapping(path = "/user/edit")
    public String showUserEditPage(Model model, Principal principal) {
        model.addAttribute("user", getActiveUserDto(principal.getName()));
        return "userEdit";
    }

    @PostMapping(path = "/user/edit")
    public ModelAndView processUserEdit(@ModelAttribute @Valid UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("userEdit", "user", userDto);

        } else {
            accountService.updateUser(userDto);
            return new ModelAndView("redirect:/user");
        }
    }

    private UserDto getActiveUserDto(String email) {
        User user = accountService.getUserByEmail(email).get();
        return accountService.convertToDto(user);
    }


}
