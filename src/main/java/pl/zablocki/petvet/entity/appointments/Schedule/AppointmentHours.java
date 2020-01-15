package pl.zablocki.petvet.entity.appointments.Schedule;

import pl.zablocki.petvet.entity.appointments.Appointment;

import java.time.LocalTime;

public class AppointmentHours {
    public LocalTime hourStart;
    public LocalTime hourEnd;
    public Appointment monday;
    public Appointment tuesday;
    public Appointment wednesday;
    public Appointment thursday;
    public Appointment friday;
    public Appointment saturday;

    public void putInDay(int dayOfWeek, Appointment appointment) {
        switch (dayOfWeek) {
            case 0:
                monday = appointment;
                break;
            case 1:
                tuesday = appointment;
                break;
            case 2:
                wednesday = appointment;
                break;
            case 3:
                thursday = appointment;
                break;
            case 4:
                friday = appointment;
                break;
            case 5:
                saturday = appointment;
                break;
        }
    }

    public LocalTime getHourStart() {
        return hourStart;
    }

    public void setHourStart(LocalTime hourStart) {
        this.hourStart = hourStart;
    }

    public LocalTime getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(LocalTime hourEnd) {
        this.hourEnd = hourEnd;
    }

    public Appointment getMonday() {
        return monday;
    }

    public void setMonday(Appointment monday) {
        this.monday = monday;
    }

    public Appointment getTuesday() {
        return tuesday;
    }

    public void setTuesday(Appointment tuesday) {
        this.tuesday = tuesday;
    }

    public Appointment getWednesday() {
        return wednesday;
    }

    public void setWednesday(Appointment wednesday) {
        this.wednesday = wednesday;
    }

    public Appointment getThursday() {
        return thursday;
    }

    public void setThursday(Appointment thursday) {
        this.thursday = thursday;
    }

    public Appointment getFriday() {
        return friday;
    }

    public void setFriday(Appointment friday) {
        this.friday = friday;
    }

    public Appointment getSaturday() {
        return saturday;
    }

    public void setSaturday(Appointment saturday) {
        this.saturday = saturday;
    }
}
