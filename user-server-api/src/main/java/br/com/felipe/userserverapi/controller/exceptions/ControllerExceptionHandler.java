package br.com.felipe.userserverapi.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandardError> resourceNotFound(final ResourceNotFoundException ex, final HttpServletRequest request){
        return ResponseEntity.status(
                NOT_FOUND).body(
                        StandardError.builder().timestamp(Instant.now())
                                .status(NOT_FOUND.value())
                                .error(NOT_FOUND.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()

        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationException> resourceNotFoundException(final MethodArgumentNotValidException ex, final HttpServletRequest request){
        var error = ValidationException.builder()
                .timestamp(Instant.now())
                .status(BAD_REQUEST.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .errors(new ArrayList<>())
                .build();

        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            error.addError(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(error);
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<StandardError> resourceNotFound(final DataIntegrityViolationException ex, final HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                StandardError.builder().timestamp(Instant.now())
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()

        );
    }

}
