package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.entity.appointments.Schedule.AppointmentHours;
import pl.zablocki.petvet.entity.appointments.Schedule.WeekSchedule;
import pl.zablocki.petvet.entity.appointments.Schedule.WeekScheduler;
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

        WeekSchedule weekSchedule = new WeekSchedule();
        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDate weekStartDate = date.minusDays(dayOfWeek - 1);


        weekSchedule.monday = weekStartDate;
        weekSchedule.tuesday = weekStartDate.plusDays(1);
        weekSchedule.wednesday = weekStartDate.plusDays(2);
        weekSchedule.thursday = weekStartDate.plusDays(3);
        weekSchedule.friday = weekStartDate.plusDays(4);
        weekSchedule.saturday = weekStartDate.plusDays(5);



        List<Appointment> weekAppointments = getAppointmentsFromWeek(weekStartDate);
        WeekScheduler weekScheduler = new WeekScheduler(weekAppointments);
        weekSchedule.appointmentHours = weekScheduler.getAppointmentHours();
        return weekSchedule;
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
