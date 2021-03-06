package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.entity.appointments.schedule.WeekSchedule;
import pl.zablocki.petvet.entity.appointments.schedule.WeekScheduler;
import pl.zablocki.petvet.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Optional<Appointment> getAppointment(long id) {
        return appointmentRepository.findById(id);
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public WeekSchedule getWeekSchedule(LocalDate date, int weekFromCurrent) {

        LocalDate weekStartDate = getWeekStartDate(date.plusWeeks(weekFromCurrent));
        List<Appointment> weekAppointments = getAppointmentsFromWeek(weekStartDate);
        return WeekScheduler.getWeekSchedule(weekAppointments,weekStartDate);
    }

    private LocalDate getWeekStartDate(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() - 1);
    }

    private List<Appointment> getAppointmentsFromWeek(LocalDate date) {
        List<Appointment> allEvents = appointmentRepository.findAll();
        List<Appointment> newEvents = new ArrayList<>();

        for (Appointment appointment : allEvents) {
            if(appointment.getStartDate().toLocalDate().isAfter(date)){
                if (appointment.getStartDate().toLocalDate().isBefore(date.plusWeeks(1))) {
                    newEvents.add(appointment);
                }
            }
        }

        return newEvents;

    }

    public Optional<Appointment> getAppointmentByDate(LocalDateTime startDate) {
       return appointmentRepository.findOneByStartDateEquals(startDate);
    }
}
