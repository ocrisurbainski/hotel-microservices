package com.urbainski.reservations.exception;

import com.urbainski.commons.exception.AbstractGenericException;
import org.springframework.http.HttpStatus;

public class ReservationCheckinException extends AbstractGenericException {

    public ReservationCheckinException(HttpStatus status, String message) {
        super(status, message);
    }

    public static ReservationCheckinException newInstanceWithStatusUnprocessableEntity(String message) {
        return new ReservationCheckinException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
