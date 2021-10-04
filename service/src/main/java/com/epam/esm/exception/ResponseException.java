package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ResponseException {
    private String errorMessage;
    private Integer errorCode;
}
