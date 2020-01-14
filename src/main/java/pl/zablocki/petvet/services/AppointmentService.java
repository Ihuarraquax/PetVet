package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.entity.appointments.Schedule.WeekSchedule;
import pl.zablocki.petvet.repository.AppointmentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public WeekSchedule getWeekSchedule(LocalDate date) {

        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDate weekStartDate = date.minusDays(dayOfWeek - 1);

        List<Appointment> weekAppointments = getAppointmentsFromWeek(weekStartDate);

        return new WeekSchedule(weekAppointments);
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
}
