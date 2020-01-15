package pl.zablocki.petvet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.exception.AppointmentNotFoundException;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.AppointmentService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

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
    public String appointmentsHomePage(Model model) {

        model.addAttribute("weekSchedule", appointmentService.getWeekSchedule(LocalDate.now()));
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

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    @GetMapping(path = "/{id}")
    public String showAppointment(Model model, @PathVariable long id) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentService.getAppointment(id);
        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException();
        }
        model.addAttribute("appointment", appointment.get());

        return "appointments/details";
    }

    @GetMapping(path = "/approve/{id}")
    public String approveAppointment(@PathVariable long id) {
        Appointment appointment = appointmentService.getAppointment(id).get();
        appointment.setApproved(true);
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }
}
