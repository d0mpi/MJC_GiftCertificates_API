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

/**
 * Contains custom exception handlers
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see ValidationException
 * @see EntityNotFoundException
 * @see MessageSource
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    /**
     * Handles {@link ValidationException} and send response containing {@link ExceptionResponseObject}
     *
     * @param ex     {@link ValidationException} to be handled
     * @param locale {@link Locale} received from http header
     * @return {@link ExceptionResponseObject} witch contains error message and code
     */
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex, Locale locale) {
        return new ResponseEntity<>(
                new ExceptionResponseObject(
                        getLocalizeMessage(ex.getClass().getSimpleName(),
                                ex.getMessage(), ex.getErrorCode(), locale), ex.getErrorCode()),
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handles {@link EntityNotFoundException} and send response containing {@link ExceptionResponseObject}
     *
     * @param ex     {@link EntityNotFoundException} to be handled
     * @param locale {@link Locale} received from http header
     * @return {@link ExceptionResponseObject} witch contains error message and code
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, Locale locale) {
        return new ResponseEntity<>(
                new ExceptionResponseObject(
                        getLocalizeMessage(ex.getClass().getSimpleName(),
                                ex.getMessage(), ex.getErrorCode(), locale), ex.getErrorCode()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CustomDataIntegrityViolationException.class)
    public ResponseEntity<?> handleCustomDataIntegrityViolationException(CustomDataIntegrityViolationException ex, Locale locale) {
        return new ResponseEntity<>(
                new ExceptionResponseObject(
                        getLocalizeMessage(ex.getClass().getSimpleName(),
                                ex.getMessage(), ex.getErrorCode(), locale), ex.getErrorCode()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getLocalizeMessage(String className,
                                      String messageType,
                                      int errorCode,
                                      Locale locale) {
        String errorMessage = messageSource.getMessage(
                className + "." + messageType, new Object[]{}, locale);
        log.error(messageSource.getMessage(
                className + "." + messageType, new Object[]{},
                new Locale("US_us")) + " : " + errorCode);
        return errorMessage;
    }


}
