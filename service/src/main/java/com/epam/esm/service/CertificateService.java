package com.epam.esm.service;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;

public interface CertificateService extends EntityService<CertificateDTO> {
    CertificateDTO update(CertificateDTO entity) throws ValidationException;

    CertificateDTO addTagToCertificate(long certificateId, TagDTO tag);

    void deleteTagFromCertificate(long certificateId, TagDTO tag);
}
