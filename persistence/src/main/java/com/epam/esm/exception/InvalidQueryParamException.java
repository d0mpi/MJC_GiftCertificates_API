package com.epam.esm.exception;

public class InvalidQueryParamException extends RuntimeException{
    public InvalidQueryParamException() {
    }

    public InvalidQueryParamException(String message) {
        super(message);
    }

    public InvalidQueryParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryParamException(Throwable cause) {
        super(cause);
    }
}
