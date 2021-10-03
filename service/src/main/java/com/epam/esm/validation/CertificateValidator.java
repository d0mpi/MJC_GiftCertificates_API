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
            throw new ValidationException();
        } else if (certificate.getName().length() > 45 || certificate.getName().isBlank()) {
            throw new ValidationException();
        }
        if (certificate.getDuration() == null) {
            throw new ValidationException();
        } else if (certificate.getDuration() < 1) {
            throw new ValidationException();
        }
        if (certificate.getPrice() == null) {
            throw new ValidationException();
        } else if (certificate.getPrice().compareTo(new BigDecimal(0)) < 0) {
            throw new ValidationException();
        }
        if (certificate.getDescription() == null) {
            throw new ValidationException();
        } else if (certificate.getDescription().length() > 255 || certificate.getDescription().isBlank()) {
            throw new ValidationException();
        }
    }
}
