package pl.zablocki.petvet.entity.appointments;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityHolderTest {


    @Test
    public void holdPeriod() {
        AvailabilityHolder availabilityHolder = new AvailabilityHolder();
        availabilityHolder.addPeriod(LocalDate.of(2020, 1, 12), LocalTime.of(15, 30), LocalTime.of(20, 0));
        Assert.assertEquals(1,availabilityHolder.availableTerm.size());
    }

    @Test
    public void getPeriods() {
        AvailabilityHolder availabilityHolder = new AvailabilityHolder();
        availabilityHolder.addPeriod(LocalDate.of(2020, 1, 12), LocalTime.of(15, 30), LocalTime.of(20, 0));
        availabilityHolder.addPeriod(LocalDate.of(2020, 1, 13), LocalTime.of(8, 30), LocalTime.of(18, 0));



        Assert.assertEquals(1,availabilityHolder.availableTerm.size());
    }
}