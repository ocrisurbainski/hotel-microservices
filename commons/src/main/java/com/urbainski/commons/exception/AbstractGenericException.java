package com.urbainski.commons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractGenericException extends RuntimeException {

    private final HttpStatus status;

    public AbstractGenericException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
