package com.epam.esm.service.impl;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.searcher.CertificateSearcher;
import com.epam.esm.validation.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicCertificateService implements CertificateService {
    @Autowired
    private final CertificateRepository repo;
    @Autowired
    private final CertificateMapper certificateMapper;
    @Autowired
    private final TagMapper tagMapper;
    @Autowired
    public final CertificateValidator certificateValidator;

    @Override
    public CertificateDTO create(CertificateDTO certificate) throws ValidationException {
        certificateValidator.validate(certificate);
        certificate.setCreate_date(LocalDateTime.now());
        certificate.setLast_update_date(LocalDateTime.now());
        return certificateMapper.convertToDto(repo.create(certificateMapper.convertToEntity(certificate)));
    }

    @Override
    public CertificateDTO read(long id) {
        return certificateMapper.convertToDto(repo.read(id));
    }

    @Override
    public List<CertificateDTO> findByCriteria(Map<String, String> paramMap) {
        return repo.findByCriteria(new CertificateSearcher().getQuery(paramMap))
                .stream()
                .map(certificateMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO update(CertificateDTO certificate) throws ValidationException {
        certificateValidator.validate(certificate);
        certificate.setLast_update_date(LocalDateTime.now());
        return certificateMapper.convertToDto(repo.update(certificateMapper.convertToEntity(certificate)));
    }

    @Override
    public CertificateDTO addTagToCertificate(long certificateId, TagDTO tag) {
        repo.addTagToCertificate(certificateId, tagMapper.convertToEntity(tag));
        return certificateMapper.convertToDto(repo.read(certificateId));
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, TagDTO tag){
        repo.deleteTagFromCertificate(certificateId, tagMapper.convertToEntity(tag));
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

    @Override
    public void delete(CertificateDTO certificate) {
        repo.delete(certificate.getId());
    }
}
