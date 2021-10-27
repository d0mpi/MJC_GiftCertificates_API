package com.epam.esm.exception;

public class InvalidQueryOrderParamException extends RuntimeException{
    public InvalidQueryOrderParamException() {
    }

    public InvalidQueryOrderParamException(String message) {
        super(message);
    }

    public InvalidQueryOrderParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryOrderParamException(Throwable cause) {
        super(cause);
    }
}
