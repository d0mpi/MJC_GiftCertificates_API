package com.epam.esm.validation;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CertificateValidator implements EntityValidator<CertificateDTO> {

    @Override
    public void validate(CertificateDTO certificate) throws ValidationException {
        if (certificate.getName() == null) {
            throw new ValidationException("certificate.nameNull", 42201);
        } else if (certificate.getName().length() > 45 || certificate.getName().isBlank()) {
            throw new ValidationException("certificate.nameSize", 42201);
        }
        if (certificate.getDuration() == null) {
            throw new ValidationException("certificate.durationNull", 42201);
        } else if (certificate.getDuration() < 1) {
            throw new ValidationException("certificate.durationNegative", 42201);
        }
        if (certificate.getPrice() == null) {
            throw new ValidationException("certificate.priceNull", 42201);
        } else if (certificate.getPrice().compareTo(new BigDecimal(0)) < 0) {
            throw new ValidationException("certificate.priceNegative", 42201);
        }
        if (certificate.getDescription() == null) {
            throw new ValidationException("certificate.descriptionNull", 42201);
        } else if (certificate.getDescription().length() > 255 || certificate.getDescription().isBlank()) {
            throw new ValidationException("certificate.descriptionSize", 42201);
        }
    }
}
