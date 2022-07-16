package com.urbainski.reservasapi.exception;

import com.urbainski.reservasapi.util.SystemMessages;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractGenericException {

    public NotFoundException(SystemMessages systemMessages) {
        super(HttpStatus.NOT_FOUND, systemMessages);
    }

}
