package com.epam.esm.exception;

public class OrderIsAssociatedWithDatabaseEntity extends RuntimeException{
    public OrderIsAssociatedWithDatabaseEntity() {
    }

    public OrderIsAssociatedWithDatabaseEntity(String message) {
        super(message);
    }

    public OrderIsAssociatedWithDatabaseEntity(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderIsAssociatedWithDatabaseEntity(Throwable cause) {
        super(cause);
    }
}
