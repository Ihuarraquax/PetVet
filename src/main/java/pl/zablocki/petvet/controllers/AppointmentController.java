package pl.zablocki.petvet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.AppointmentService;

import java.security.Principal;

@RequestMapping(path = "/appointments")
@Controller
public class AppointmentController {

    private final AccountService accountService;
    private final AppointmentService appointmentService;

    public AppointmentController(AccountService accountService, AppointmentService appointmentService) {
        this.accountService = accountService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String appointmentsHomePage() {
        return "appointments/home";
    }

    @GetMapping(path = "/add")
    public String showAppointmentForm(Model model, Principal principal) {

        Appointment appointment = new Appointment();
        appointment.setOwner(accountService.getOwnerByEmail(principal.getName()).get());
        model.addAttribute("vets", accountService.getAllVets());
        model.addAttribute("appointment", appointment);
        return "appointments/appointmentForm";
    }
    @PostMapping(path = "/add")
    public String addAppointment(@ModelAttribute Appointment appointment) {

        appointmentService.addAppointment(appointment);
        return "redirect:/appointments";
    }
}
