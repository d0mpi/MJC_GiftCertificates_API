package com.epam.esm.mapper;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CertificateMapper {
    @Autowired
    protected final ModelMapper mapper;

    public CertificateDTO convertToDto(Certificate certificate) {
        return Objects.isNull(certificate) ? null : mapper.map(certificate, CertificateDTO.class);
    }

    public Certificate convertToEntity(CertificateDTO certificateDTO) {
        return Objects.isNull(certificateDTO) ? null : mapper.map(certificateDTO, Certificate.class);
    }
}
