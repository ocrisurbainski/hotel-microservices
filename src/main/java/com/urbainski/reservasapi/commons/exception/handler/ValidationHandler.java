package com.urbainski.reservasapi.commons.exception.handler;

import com.urbainski.reservasapi.commons.exception.AbstractGenericException;
import com.urbainski.reservasapi.commons.exception.handler.dto.ErrorDTO;
import com.urbainski.reservasapi.commons.exception.handler.dto.ErrorFieldDTO;
import com.urbainski.reservasapi.commons.exception.handler.dto.ResponseErrorDTO;
import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import com.urbainski.reservasapi.commons.message.SystemMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ValidationHandler {

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    public ValidationHandler(MessageSourceWrapperComponent messageSourceWrapperComponent) {
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
    }

    @ExceptionHandler(AbstractGenericException.class)
    public ResponseEntity<ResponseErrorDTO> handlerAbstractGenericException(AbstractGenericException exception) {

        Objects.requireNonNull(exception);
        var dto = ResponseErrorDTO.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(dto);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ResponseErrorDTO> handleWebExchangeBindException(WebExchangeBindException ex) {
        var errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::convertToErrorDTO)
                .collect(Collectors.toList());
        var responseError = ResponseErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(messageSourceWrapperComponent.getMessage(SystemMessages.VALIDATION_FAILED))
                .details(errors)
                .build();
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseErrorDTO> handleDuplicateKeyException(DuplicateKeyException ex) {

        var message = "";
        if (ex != null && ex.getMessage() != null) {
            try {
                message = ex.getMessage().substring(ex.getMessage().indexOf("index:") + 6);
                message = message.substring(0, message.indexOf("'"));
                message = message.trim();
            } catch (Exception ex2) {
                log.error(ex2.getMessage(), ex2);
                message = ex.getMessage();
            }
        }

        var field = "";
        if (!message.isEmpty()) {
            try {
                field = message.substring(message.indexOf("{") + 1);
                field = field.substring(0, field.indexOf(":"));
                field = field.trim();
            } catch (Exception ex2) {
                log.error(ex2.getMessage(), ex2);
                field = "";
            }
        }

        var errorDto = field.isEmpty() ? new ErrorDTO(message) : new ErrorFieldDTO(field, message);
        var responseError = ResponseErrorDTO.builder()
                .code(HttpStatus.CONFLICT)
                .message(messageSourceWrapperComponent.getMessage(SystemMessages.DATA_DUPLICATION))
                .details(List.of(errorDto))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseError);
    }

    private ErrorDTO convertToErrorDTO(ObjectError objectError) {
        String message = objectError.getDefaultMessage();

        String field = null;
        if (objectError instanceof FieldError) {
            field = ((FieldError) objectError).getField();
        }

        if (field != null) {
            return new ErrorFieldDTO(field, message);
        }

        return new ErrorDTO(message);
    }

}
