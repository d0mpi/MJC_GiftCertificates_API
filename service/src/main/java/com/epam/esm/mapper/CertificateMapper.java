package com.epam.esm.mapper;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Contains method required for conversion object from
 * {@link CertificateDTO} to {@link Certificate} and vice-verse
 * with the help of {@link ModelMapper}
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see CertificateDTO
 * @see java.security.cert.Certificate
 */
@Component
@RequiredArgsConstructor
public class CertificateMapper {
    @Autowired
    protected final ModelMapper mapper;

    /**
     * Converts {@link Certificate} to {@link CertificateDTO} with the help of {@link ModelMapper}
     *
     * @param certificate {@link Certificate} to be converted
     * @return {@link CertificateDTO} converted from the specified {@link Certificate}
     */
    public CertificateDTO convertToDto(Certificate certificate) {
        return Objects.isNull(certificate) ? null : mapper.map(certificate, CertificateDTO.class);
    }

    /**
     * Converts {@link CertificateDTO} to {@link Certificate} with the help of {@link ModelMapper}
     *
     * @param certificateDTO {@link CertificateDTO} to be converted
     * @return {@link Certificate} converted from the specified {@link Certificate}
     */
    public Certificate convertToEntity(CertificateDTO certificateDTO) {
        return Objects.isNull(certificateDTO) ? null : mapper.map(certificateDTO, Certificate.class);
    }
}
