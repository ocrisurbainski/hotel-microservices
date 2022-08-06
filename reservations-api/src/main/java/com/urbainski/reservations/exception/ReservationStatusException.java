package com.urbainski.reservations.exception;

import com.urbainski.commons.exception.AbstractGenericException;
import org.springframework.http.HttpStatus;

public class ReservationStatusException extends AbstractGenericException {

    public ReservationStatusException(HttpStatus status, String message) {
        super(status, message);
    }

    public static ReservationStatusException newInstanceWithStatusUnprocessableEntity(String message) {
        return new ReservationStatusException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

}
