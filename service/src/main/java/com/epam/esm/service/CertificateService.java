package com.epam.esm.service;

import com.epam.esm.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateService extends EntityService<Certificate>{
    Certificate update(Certificate entity);

    void addTagToCertificate(long certificateId, long tagId);

    void deleteTagFromCertificate(long certificateId, long tagId);
}
