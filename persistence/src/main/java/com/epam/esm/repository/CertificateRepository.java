package com.epam.esm.repository;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;

public interface CertificateRepository extends Repository<Certificate>{
    void addTagToCertificate(long certificateId, Tag tag);

    void deleteTagFromCertificate(long certificateId, Tag tag);

    Certificate update(Certificate entity);
}
