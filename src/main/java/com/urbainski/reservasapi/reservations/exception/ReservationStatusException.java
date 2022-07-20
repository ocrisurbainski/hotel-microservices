package com.urbainski.reservasapi.reservations.exception;

import com.urbainski.reservasapi.commons.exception.AbstractGenericException;
import org.springframework.http.HttpStatus;

public class ReservationStatusException extends AbstractGenericException {

    public ReservationStatusException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
