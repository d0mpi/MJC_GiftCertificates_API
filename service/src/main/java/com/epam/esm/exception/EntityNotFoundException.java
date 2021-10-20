package com.epam.esm.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {
    @Getter
    private final int errorCode;

    public EntityNotFoundException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
