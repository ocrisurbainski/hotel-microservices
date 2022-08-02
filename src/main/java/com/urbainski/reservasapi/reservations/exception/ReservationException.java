package com.urbainski.reservasapi.reservations.exception;

import com.urbainski.reservasapi.commons.exception.AbstractGenericException;
import org.springframework.http.HttpStatus;

public class ReservationException extends AbstractGenericException {

    public ReservationException(HttpStatus status, String message) {
        super(status, message);
    }

    public static ReservationException newInstanceWithStatusUnprocessableEntity(String message) {
        return new ReservationException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

}
