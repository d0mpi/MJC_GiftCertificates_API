package com.epam.esm.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private final MessageSource messageSource;

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(
                "validationException." + ex.getMessage(), new Object[]{}, locale);
        log.error(errorMessage + " : " + ex.getErrorCode());
        return new ResponseEntity<>(
                new ResponseException(errorMessage, ex.getErrorCode()), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = DAOException.class)
    public ResponseEntity<?> handleDAOException(DAOException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(
                "DAOException." + ex.getMessage(), new Object[]{}, locale);
        log.error(errorMessage + " : " + ex.getErrorCode());
        return new ResponseEntity<>(
                new ResponseException(errorMessage, ex.getErrorCode()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
