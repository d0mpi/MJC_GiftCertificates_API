package com.epam.esm.exception;

import lombok.Getter;

/**
 * Exception that occurs when invalid content was received from the client
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
public class ValidationException extends RuntimeException {
    @Getter
    private final int errorCode;

    public ValidationException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
