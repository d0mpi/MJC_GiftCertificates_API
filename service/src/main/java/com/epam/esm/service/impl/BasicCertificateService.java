package com.epam.esm.service.impl;

import com.epam.esm.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.CertificateSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BasicCertificateService implements CertificateService {
    private final CertificateRepository repo;
    private CertificateSearcher searcher;

    @Autowired
    public BasicCertificateService(CertificateRepository repo, CertificateSearcher searcher) {
        this.repo = repo;
        this.searcher = searcher;
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setCreate_date(LocalDateTime.now());
        certificate.setLast_update_date(LocalDateTime.now());
        return repo.create(certificate);
    }

    @Override
    public Certificate read(long id) {
        return repo.read(id);
    }

    @Override
    public List<Certificate> findByCriteria(Map<String, String> paramMap) {
        return repo.findByCriteria(searcher.getQuery(paramMap));
    }

    @Override
    public Certificate update(Certificate certificate) {
        certificate.setLast_update_date(LocalDateTime.now());
        return repo.update(certificate);
    }

    @Override
    public void addTagToCertificate(long certificateId, long tagId) {
        repo.addTagToCertificate(certificateId, tagId);
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, long tagId) {
        repo.deleteTagFromCertificate(certificateId, tagId);
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

    @Override
    public void delete(Certificate certificate) {
        repo.delete(certificate.getId());
    }
}
