package com.epam.esm.validation;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.EntityDTO;
import com.epam.esm.exception.ValidationException;

/**
 * Contains method required to validate received from the client {@link CertificateDTO}
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityValidator
 * @see ValidationException
 */
public interface EntityValidator<T extends EntityDTO> {
    /**
     * Validates DTO information
     *
     * @param entity {@link EntityDTO} to be validated
     * @throws ValidationException if {@link EntityDTO} is invalid
     */
    void validate(T entity);
}
