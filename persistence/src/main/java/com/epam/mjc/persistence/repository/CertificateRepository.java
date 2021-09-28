package com.epam.mjc.persistence.repository;

import com.epam.mjc.model.Certificate;

public interface CertificateRepository extends Repository<Integer, Certificate>{
    void addTagToCertificate(int certificateId, int tagId);

    void deleteTagFromCertificate(int certificateId, int tagId);
}
