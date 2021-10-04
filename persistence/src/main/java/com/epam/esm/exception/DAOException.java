package com.epam.esm.exception;

import lombok.Getter;

public class DAOException extends RuntimeException{
    @Getter
    private final int errorCode;

    public DAOException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
