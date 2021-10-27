package com.epam.esm.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Contains custom exception handlers
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityNotFoundException
 * @see MessageSource
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    /**
     * Handles {@link EntityNotFoundException} and send response containing {@link ExceptionResponseObject}
     *
     * @param ex     {@link EntityNotFoundException} to be handled
     * @param locale {@link Locale} received from http header
     * @return {@link ExceptionResponseObject} witch contains error message and code
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ExceptionResponseObject handleThrowable(Throwable ex,
                                                   Locale locale) {
        log.error(getLocalizeMessage(new Throwable("message.not-supported.exception"), new Locale("US_us")), ex);
        return ExceptionResponseObject.builder().errorCode(ErrorCode.UNSUPPORTED.getCode())
                .errorMessage(getLocalizeMessage(new Throwable("message.not-supported.exception"), locale)
                        + ex.getCause()).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ExceptionResponseObject handleEntityNotFoundException(EntityNotFoundException ex,
                                                                 Locale locale) {
        log.error(getLocalizeMessage(ex, new Locale("US_us")), ex);
        return ExceptionResponseObject.builder().errorCode(ErrorCode.ENTITY_NOT_FOUND.getCode())
                .errorMessage(getLocalizeMessage(ex, locale)).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ExceptionResponseObject handleEntityAlreadyExistsException(EntityAlreadyExistsException ex,
                                                                      Locale locale) {
        log.error(getLocalizeMessage(ex, new Locale("US_us")), ex);
        return ExceptionResponseObject.builder().errorCode(ErrorCode.ENTITY_EXISTS.getCode())
                .errorMessage(getLocalizeMessage(ex, locale)).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" && "));
        log.error(errorMessages, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionResponseObject.builder()
                .errorCode(ErrorCode.INPUT_VALIDATION.getCode())
                .errorMessage(errorMessages).build());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        log.error(getLocalizeMessage(ex, new Locale("US_us")), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponseObject.builder()
                        .errorCode(ErrorCode.NO_HANDLER.getCode())
                        .errorMessage(getLocalizeMessage(new Throwable("message.not-found.handler"),
                                request.getLocale())).build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        log.error(getLocalizeMessage(ex, new Locale("US_us")), ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ExceptionResponseObject.builder()
                        .errorCode(ErrorCode.NO_HANDLER.getCode())
                        .errorMessage(getLocalizeMessage(new Throwable("message.not-supported.method"),
                                request.getLocale())).build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = EntityNotCreatedException.class)
    public ExceptionResponseObject handleEntityNotCreatedException(EntityNotCreatedException ex,
                                                                   Locale locale) {
        log.error(getLocalizeMessage(ex, new Locale("US_us")), ex);
        return ExceptionResponseObject.builder().errorCode(ErrorCode.ENTITY_CREATE.getCode())
                .errorMessage(getLocalizeMessage(ex, locale)).build();
    }


    private String getLocalizeMessage(Throwable ex, Locale locale) {
        return messageSource.getMessage(ex.getMessage(), new Object[0], locale);
    }
}
