package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Class contains the error message and error code.
 * It is necessary to transmit information in a convenient form to the client
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
@Data
@ToString
@AllArgsConstructor
@Builder
public class ExceptionResponseObject {
    private String errorMessage;
    private Integer errorCode;
}
