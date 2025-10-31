package com.kairos.ecommerce.infrastructure.rest.controller.handler;

import com.kairos.ecommerce.infrastructure.exceptions.PriceNotFoundException;
import com.kairos.ecommerce.infrastructure.rest.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice
@Slf4j
public class RestControllerHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage(), ex);
        final var errorDTO = ErrorDTO.builder().code("INVALID_ARGUMENT_ERROR").message(ex.getMessage()).build();
        return badRequest().body(errorDTO);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage(), ex);
        final var errorDTO = ErrorDTO.builder().code("REQUIRED_FIELD_ERROR").message(ex.getMessage()).build();
        return badRequest().body(errorDTO);
    }

    @ExceptionHandler(PriceNotFoundException.class)
    protected ResponseEntity<ErrorDTO> handlePriceNotFoundException(PriceNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        final var error = ErrorDTO.builder().code("PRICE_NOT_FOUND").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDTO> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        final var message = "There was an error processing the request.";
        final var error = ErrorDTO.builder().code("INTERNAL_SERVER_ERROR").message(message).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
