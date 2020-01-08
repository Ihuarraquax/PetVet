package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zablocki.petvet.entity.appointments.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
