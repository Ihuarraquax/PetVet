package pl.zablocki.petvet.entity.appointments.Schedule;

import pl.zablocki.petvet.entity.appointments.Appointment;

import java.util.List;

public class WeekScheduler {
    private final AppointmentHours[] appointmentHours;
    private final int HOURS = 8;

    public WeekScheduler(List<Appointment> weekAppointments) {

        appointmentHours = new AppointmentHours[HOURS];
        for (int i = 0; i < HOURS; i++) {
            appointmentHours[i] = new AppointmentHours();
        }

        for (Appointment appointment : weekAppointments) {
            int dayOfWeek = appointment.getStartDate().getDayOfWeek().getValue() - 1;
            int hour = appointment.getStartDate().getHour();
            appointmentHours[hour-8].putInDay(hour,dayOfWeek, appointment);
        }
    }

    public AppointmentHours[] getAppointmentHours() {
        return appointmentHours;
    }
}
