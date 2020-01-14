package pl.zablocki.petvet.entity.appointments.Schedule;

import pl.zablocki.petvet.entity.appointments.Appointment;

import java.util.List;

public class WeekSchedule {
    public final Appointment[][] weekSchedule;
    private final int DAYS_OF_WEEK = 7;
    private final int HOURS = 8;

    public WeekSchedule(List<Appointment> weekAppointments) {
        weekSchedule = new Appointment[DAYS_OF_WEEK][HOURS];
        for (Appointment appointment : weekAppointments) {
            int dayOfWeek = appointment.getStartDate().getDayOfWeek().getValue() - 1;
            int hour = appointment.getStartDate().getHour();
            weekSchedule[dayOfWeek][hour-8] = appointment;
        }
    }


}
