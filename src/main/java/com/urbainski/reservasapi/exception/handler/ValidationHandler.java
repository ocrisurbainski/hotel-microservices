package com.urbainski.reservasapi.exception.handler;

import com.urbainski.reservasapi.exception.NotFoundException;
import com.urbainski.reservasapi.exception.handler.dto.ErrorDTO;
import com.urbainski.reservasapi.exception.handler.dto.ErrorFieldDTO;
import com.urbainski.reservasapi.exception.handler.dto.ResponseErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ValidationHandler {

    private final MessageSource messageSource;

    public ValidationHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handlerNotFoundException(NotFoundException exception) {
        var dto = ResponseErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::convertToErrorDTO)
                .collect(Collectors.toList());
        var responseError = ResponseErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(messageSource.getMessage("msg.validation.failed", null, Locale.getDefault()))
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
                .message(messageSource.getMessage("msg.duplication.key", null, Locale.getDefault()))
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
