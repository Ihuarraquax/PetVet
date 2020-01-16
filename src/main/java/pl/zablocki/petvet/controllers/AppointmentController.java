package pl.zablocki.petvet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zablocki.petvet.entity.Examination;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.exception.AppointmentNotFoundException;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.AppointmentService;
import pl.zablocki.petvet.services.ExaminationService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@SessionAttributes("appointmentSession")
@RequestMapping(path = "/appointments")
@Controller
public class AppointmentController {

    private final AccountService accountService;
    private final AppointmentService appointmentService;
    private final ExaminationService examinationService;

    public AppointmentController(AccountService accountService, AppointmentService appointmentService, ExaminationService examinationService) {
        this.accountService = accountService;
        this.appointmentService = appointmentService;
        this.examinationService = examinationService;
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

    @GetMapping(path = "/examination/{id}")
    public String startExamination(@PathVariable long id, Model model, @ModelAttribute Appointment appointmentSession) {
        appointmentSession = appointmentService.getAppointment(id).get();
        Examination examination = new Examination();
        model.addAttribute("examination", examination);
        return "appointments/examination";
    }

    @PostMapping(path = "/examination/{id}")
    public String saveExamination(@ModelAttribute Examination examination, @PathVariable long id) {
        Appointment appointment = appointmentService.getAppointment(id).get();
        examination.setPet(appointment.getPet());
        examination.setVet(appointment.getVet());
        examinationService.saveExamination(examination);
        return "redirect:/appointments";
    }


    @ModelAttribute("appointmentSession")
    public Appointment addSessionAppointment() {
        return new Appointment();
    }
}
