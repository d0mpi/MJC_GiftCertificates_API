package com.epam.esm.repository;

import com.epam.esm.Certificate;
import com.epam.esm.util.CertificateSearcher;

import java.util.List;

public interface CertificateRepository extends Repository<Certificate>{
    void addTagToCertificate(long certificateId, long tagId);

    void deleteTagFromCertificate(long certificateId, long tagId);

    Certificate update(Certificate entity);
}
