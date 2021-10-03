package com.epam.esm.validation;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class TagValidator implements EntityValidator<TagDTO> {
    @Override
    public void validate(TagDTO tag) throws ValidationException {
        if (tag.getName() == null) {
            throw new ValidationException();
        } else if (tag.getName().length() > 45 || tag.getName().isBlank()) {
            throw new ValidationException();
        }
    }
}
