package pl.zablocki.petvet.entity.appointments.Schedule;

import pl.zablocki.petvet.entity.appointments.Appointment;

import java.time.LocalDate;

public class WeekSchedule {
    public LocalDate monday;
    public LocalDate tuesday;
    public LocalDate wednesday;
    public LocalDate thursday;
    public LocalDate friday;
    public LocalDate saturday;
    public AppointmentHours[] appointmentHours;
}
