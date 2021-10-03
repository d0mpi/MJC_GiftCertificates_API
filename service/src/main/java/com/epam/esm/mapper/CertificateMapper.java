package com.epam.esm.mapper;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CertificateMapper {
    protected final ModelMapper mapper;

    @Autowired
    public CertificateMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CertificateDTO convertToDto(Certificate certificate) {
        return Objects.isNull(certificate) ? null : mapper.map(certificate, CertificateDTO.class);
    }

    public Certificate convertToEntity(CertificateDTO certificateDTO) {
        return Objects.isNull(certificateDTO) ? null : mapper.map(certificateDTO, Certificate.class);
    }
}
