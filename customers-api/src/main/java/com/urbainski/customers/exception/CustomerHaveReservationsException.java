package com.urbainski.customers.exception;

import com.urbainski.commons.exception.AbstractGenericException;
import org.springframework.http.HttpStatus;

public class CustomerHaveReservationsException extends AbstractGenericException {

    public CustomerHaveReservationsException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

}
