package com.epam.esm.validation;

import com.epam.esm.DTO.EntityDTO;
import com.epam.esm.exception.ValidationException;

public interface EntityValidator<T extends EntityDTO> {
    void validate(T entity) throws ValidationException;
}
