package com.urbainski.reservasapi.commons.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractGenericException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
