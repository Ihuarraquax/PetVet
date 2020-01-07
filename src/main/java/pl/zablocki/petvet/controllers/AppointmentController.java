package pl.zablocki.petvet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zablocki.petvet.services.AccountService;

import java.security.Principal;

@RequestMapping(path = "/appointments")
@Controller
public class AppointmentController {

    private final AccountService accountService;

    public AppointmentController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String appointmentsHomePage() {
        return "appointments/home";
    }

    @GetMapping(path = "/add")
    public String addAppointment(Model model, Principal principal) {

        return "appointments/appointmentForm";
    }
}
