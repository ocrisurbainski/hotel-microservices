package com.urbainski.reservasapi.reservations.exception;

import com.urbainski.reservasapi.commons.exception.AbstractGenericException;
import org.springframework.http.HttpStatus;

public class ReservationCheckinException extends AbstractGenericException {

    public ReservationCheckinException(HttpStatus status, String message) {
        super(status, message);
    }

    public static ReservationCheckinException newInstanceWithStatusUnprocessableEntity(String message) {
        return new ReservationCheckinException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
