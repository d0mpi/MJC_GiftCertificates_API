package com.epam.esm.validation;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Service;

/**
 * Contains method required to validate received from the client {@link TagDTO}
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityValidator
 * @see ValidationException
 */
@Service
public class TagValidator implements EntityValidator<TagDTO> {

    /**
     * Validates DTO information
     *
     * @param tag {@link TagDTO} to be validated
     * @throws ValidationException if {@link TagDTO} is invalid
     */
    @Override
    public void validate(TagDTO tag) throws ValidationException {
        if (tag.getName() == null) {
            throw new ValidationException("tag.nameNull", 42202);
        } else if (tag.getName().length() > 45 || tag.getName().isBlank()) {
            throw new ValidationException("tag.nameSize", 42202);
        }
    }
}
