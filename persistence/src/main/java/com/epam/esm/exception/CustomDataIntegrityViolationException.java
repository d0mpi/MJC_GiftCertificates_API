package com.epam.esm.exception;

import lombok.Getter;

public class CustomDataIntegrityViolationException extends RuntimeException{
    @Getter
    private final int errorCode;

    public CustomDataIntegrityViolationException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
