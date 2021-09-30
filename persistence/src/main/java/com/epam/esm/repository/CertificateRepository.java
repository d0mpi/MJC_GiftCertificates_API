package com.epam.esm.repository;

import com.epam.esm.Certificate;

public interface CertificateRepository extends Repository<Certificate>{
    void addTagToCertificate(long certificateId, long tagId);

    void deleteTagFromCertificate(long certificateId, long tagId);
}
