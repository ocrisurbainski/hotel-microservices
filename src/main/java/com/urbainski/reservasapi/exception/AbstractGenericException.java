package com.urbainski.reservasapi.exception;

import com.urbainski.reservasapi.util.SystemMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractGenericException extends RuntimeException {

    private final HttpStatus status;
    private final SystemMessages systemMessages;

    public AbstractGenericException(HttpStatus status, SystemMessages systemMessages) {
        this.status = status;
        this.systemMessages = systemMessages;
    }

}
