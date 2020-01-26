package pl.zablocki.petvet.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zablocki.petvet.entity.Examination;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.AppointmentService;
import pl.zablocki.petvet.services.ExaminationService;

import java.security.Principal;
import java.util.Optional;

@SessionAttributes("appointmentSession")
@RequestMapping(path = "/examination")
@Controller
public class ExaminationController {
    private final AccountService accountService;
    private final AppointmentService appointmentService;
    private final ExaminationService examinationService;

    public ExaminationController(AccountService accountService, AppointmentService appointmentService, ExaminationService examinationService) {
        this.accountService = accountService;
        this.appointmentService = appointmentService;
        this.examinationService = examinationService;
    }

    @GetMapping(path = "all")
    public String getExaminationListPageable(Model model, Pageable pageable) {
        model.addAttribute("examinations", examinationService.findAll(pageable));
        return "examination/examinations";
    }

    @GetMapping(path = "my")
    public String getOwnerExaminationListPageable(Model model, Pageable pageable, Principal principal) {

        Optional<Owner> owner = accountService.getOwnerByEmail(principal.getName());
        model.addAttribute("examinations", examinationService.findAllByOwner(owner.get(), pageable));
        return "examination/examinations";
    }

    @GetMapping(path = "details/{id}")
    public String getDetails(@PathVariable long id, Model model) {
        Optional<Examination> examination = examinationService.findExaminationById(id);
        model.addAttribute("examination", examination.get());
        return "examination/examinationDetails";
    }

    @GetMapping(path = "start/{id}")
    public String startExamination(@PathVariable long id, Model model, @ModelAttribute Appointment appointmentSession) {
        appointmentSession = appointmentService.getAppointment(id).get();
        Examination examination = new Examination();
        model.addAttribute("examination", examination);
        return "examination/examinationAdd";
    }

    @PostMapping(path = "start/{id}")
    public String saveExamination(@ModelAttribute Examination examination, @PathVariable long id) {
        Appointment appointment = appointmentService.getAppointment(id).get();
        examination.setOwner(appointment.getOwner());
        examination.setPet(appointment.getPet());
        examination.setVet(appointment.getVet());
        examination.setDate(appointment.getStartDate());
        examinationService.saveExamination(examination);
        return "redirect:/appointment";
    }
}
