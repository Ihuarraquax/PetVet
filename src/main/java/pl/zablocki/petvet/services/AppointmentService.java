package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
