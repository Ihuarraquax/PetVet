package pl.zablocki.petvet.entity.appointments.schedule;

import pl.zablocki.petvet.entity.appointments.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WeekScheduler {
    private final static int HOURS = 8;


    private static AppointmentHours[] getAppointmentHours(List<Appointment> weekAppointments) {

        AppointmentHours[] appointmentHours = new AppointmentHours[HOURS];
        for (int i = 0; i < HOURS; i++) {
            appointmentHours[i] = new AppointmentHours();
            appointmentHours[i].setHourStart(LocalTime.of(i+HOURS,0));
            appointmentHours[i].setHourEnd(LocalTime.of(i+HOURS+1,0));
        }

        for (Appointment appointment : weekAppointments) {
            int dayOfWeek = appointment.getStartDate().getDayOfWeek().getValue() - 1;
            int hour = appointment.getStartDate().getHour();
            appointmentHours[hour-HOURS].putInDay(dayOfWeek, appointment);
        }
        return appointmentHours;
    }


    public static WeekSchedule getWeekSchedule(List<Appointment> weekAppointments, LocalDate weekStartDate) {
        WeekSchedule weekSchedule = new WeekSchedule(getAppointmentHours(weekAppointments));
        weekSchedule.monday = weekStartDate;
        weekSchedule.tuesday = weekStartDate.plusDays(1);
        weekSchedule.wednesday = weekStartDate.plusDays(2);
        weekSchedule.thursday = weekStartDate.plusDays(3);
        weekSchedule.friday = weekStartDate.plusDays(4);
        weekSchedule.saturday = weekStartDate.plusDays(5);
        return weekSchedule;
    }
}
