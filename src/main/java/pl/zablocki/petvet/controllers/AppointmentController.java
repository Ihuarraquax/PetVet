package pl.zablocki.petvet.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.exception.AppointmentNotFoundException;
import pl.zablocki.petvet.exception.InvalidAppointmentDate;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.AppointmentService;
import pl.zablocki.petvet.services.EmailSenderService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@RequestMapping(path = "/appointment")
@Controller
public class AppointmentController {

    private final EmailSenderService emailSenderService;
    private final AccountService accountService;
    private final AppointmentService appointmentService;

    public AppointmentController(EmailSenderService emailSenderService, AccountService accountService, AppointmentService appointmentService) {
        this.emailSenderService = emailSenderService;
        this.accountService = accountService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String appointmentsHomePage(Model model, @RequestParam(value = "week", defaultValue = "0", required = false) int week, Principal principal) {
        Optional<User> userByEmail = accountService.getUserByEmail(principal.getName());
        model.addAttribute("user", userByEmail.get());
        model.addAttribute("weekSchedule", appointmentService.getWeekSchedule(LocalDate.now(), week));

        return "appointment/home";

    }

    @GetMapping(path = "/add")
    public String showAppointmentForm(Model model, Principal principal) {

        Appointment appointment = new Appointment();
        appointment.setOwner(accountService.getOwnerByEmail(principal.getName()).get());
        model.addAttribute("vets", accountService.getAllVets());
        model.addAttribute("appointment", appointment);
        return "appointment/appointmentForm";
    }

    @PostMapping(path = "/add")
    public String addAppointment(@ModelAttribute Appointment appointment, Principal principal) throws InvalidAppointmentDate {
        appointment.setStartDate(appointment.getStartDate().withMinute(0).withSecond(0));
        validAppointmentDate(appointment.getStartDate());
        Optional<Owner> ownerByEmail = accountService.getOwnerByEmail(principal.getName());
        appointment.setOwner(ownerByEmail.get());
        appointment.setApproved(false);

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointment";
    }

    private void validAppointmentDate(LocalDateTime startDate) throws InvalidAppointmentDate {
        if (startDate.getDayOfWeek().getValue()==7) {
            throw new InvalidAppointmentDate("W niedziele gabinet jest nieczynny, prosze wybrać inną datę.");
        }

        if(startDate.getHour()>15 || startDate.getHour()<8){
            throw new InvalidAppointmentDate("Gabinet jest czynny tylko w godzinach 8-16. Proszę wybrać inną godzinę");
        }

        if(appointmentService.getAppointmentByDate(startDate).isPresent()){
            throw new InvalidAppointmentDate("Istnieje już inna wizyta zapisana na tą datę");
        }

    }

    @GetMapping(path = "/{id}")
    public String showAppointment(Model model, @PathVariable long id) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentService.getAppointment(id);
        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException();
        }
        model.addAttribute("appointment", appointment.get());

        return "appointment/details";
    }

    @GetMapping(path = "/approve/{id}")
    public String approveAppointment(@PathVariable long id) {
        Appointment appointment = appointmentService.getAppointment(id).get();
        appointment.setApproved(true);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("PetVet");
        simpleMailMessage.setTo(appointment.getOwner().getUser().getEmail());
        simpleMailMessage.setSubject("Twoja wizyta została zatwierdzona!");
        simpleMailMessage.setText(appointment.getOwner().getCredentials() + " twoja wizyta z " + appointment.getPet().getName() + " została zatwierdzona");

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> emailSenderService.sendEmail(simpleMailMessage));

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointment";
    }


    @ModelAttribute("appointmentSession")
    public Appointment addSessionAppointment() {
        return new Appointment();
    }
}
