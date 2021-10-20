package com.epam.esm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that occurs when invalid content was received from the client
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException {
    @Getter
    private final int errorCode;

    public ValidationException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
