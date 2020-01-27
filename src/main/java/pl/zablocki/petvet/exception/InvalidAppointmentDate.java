package pl.zablocki.petvet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidAppointmentDate extends RuntimeException {
    public InvalidAppointmentDate(String message) {
        super(message);
    }
}
