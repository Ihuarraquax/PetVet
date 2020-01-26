package pl.zablocki.petvet.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.dto.ConfirmationToken;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.repository.ConfirmationTokenRepository;
import pl.zablocki.petvet.repository.UserRepository;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.EmailSenderService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private AccountService accountService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;

    public RegistrationController(AccountService accountService, UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService) {
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping(path = "/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping(path = "/registration")
    public ModelAndView registerUserAccount(ModelAndView modelAndView,@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        } else {
            Owner ownerAccount = accountService.createOwnerAccount(accountDto.getEmail(), accountDto.getPassword());
            ConfirmationToken confirmationToken = new ConfirmationToken(ownerAccount.getUser());

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(accountDto.getEmail());
            mailMessage.setSubject("Potwierdź rejestracje!");
            mailMessage.setFrom("PetVet@gmail.com");
            mailMessage.setText("Kliknij tutaj aby potwierdzić rejestracje : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("email", ownerAccount.getUser().getEmail());

            modelAndView.setViewName("/registration/successfulRegisteration");
        }
        return modelAndView;
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("/registration/accountVerified");
        }
        else
        {
            modelAndView.addObject("message","Link jest niepoprawny!");
            modelAndView.setViewName("/registration/error");
        }

        return modelAndView;
    }

}