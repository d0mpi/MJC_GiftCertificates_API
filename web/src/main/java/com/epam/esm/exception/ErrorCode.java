package com.epam.esm.exception;

public enum ErrorCode {
    INPUT_VALIDATION(40001),
    INVALID_QUERY(40002),
    NO_HANDLER(40402),
    UNSUPPORTED(50000),
    ENTITY_NOT_FOUND(50001),
    ENTITY_CREATE(50002),
    ENTITY_EXISTS(50003),
    ENTITY_ASSOCIATED(50004);

    private final int i;

    ErrorCode(int i) {
        this.i = i;
    }

    public int getCode() {
        return i;
    }
}
