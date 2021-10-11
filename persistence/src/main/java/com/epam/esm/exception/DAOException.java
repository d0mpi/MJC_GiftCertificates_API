package com.epam.esm.exception;

import lombok.Getter;

/**
 * Exception that occurs when smth went wrong during receive information
 * from the database
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
public class DAOException extends RuntimeException {
    @Getter
    private final int errorCode;

    public DAOException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
