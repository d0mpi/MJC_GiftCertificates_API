package com.epam.esm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException{
    @Getter
    private final int errorCode;

    public ValidationException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
