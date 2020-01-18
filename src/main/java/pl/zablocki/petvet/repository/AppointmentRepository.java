package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.entity.appointments.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
