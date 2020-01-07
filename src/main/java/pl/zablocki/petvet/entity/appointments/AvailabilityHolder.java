package pl.zablocki.petvet.entity.appointments;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityHolder {
    List<Period> availableTerm;

    public AvailabilityHolder() {
        availableTerm = new ArrayList<>();
    }

    public void addPeriod(LocalDate day, LocalTime start, LocalTime end) {
        availableTerm.add(new Period(day, start, end));
    }


    private class Period {
        LocalDate day;
        LocalTime start;
        LocalTime end;

        public Period(LocalDate day, LocalTime start, LocalTime end) {
            this.day = day;
            this.start = start;
            this.end = end;
        }
    }
}
