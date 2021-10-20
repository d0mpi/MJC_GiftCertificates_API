package com.epam.esm.service.impl;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validation.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Basic implementation of the {@link CertificateService} class.
 * Transfer data to {@link com.epam.esm.repository.impl.JdbcCertificateRepository}
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see CertificateService
 */
@Service
@RequiredArgsConstructor
public class BasicCertificateService implements CertificateService {
    @Autowired
    private final CertificateRepository certificateRepo;
    @Autowired
    private final TagRepository tagRepo;
    @Autowired
    private final CertificateMapper certificateMapper;
    @Autowired
    private final TagMapper tagMapper;
    @Autowired
    private final CertificateValidator certificateValidator;


    @Override
    public CertificateDTO create(CertificateDTO certificate) {
        certificateValidator.validate(certificate);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateMapper
                .convertToDto(
                        certificateRepo.create(
                                        certificateMapper.convertToEntity(certificate))
                                .orElseThrow(() -> (new EntityNotFoundException("certificate", 40401))));
    }

    @Override
    public CertificateDTO read(long id) {
        return certificateMapper.convertToDto(
                certificateRepo.read(id)
                        .orElseThrow(() -> (new EntityNotFoundException("certificate", 40401))));
    }

    @Override
    public List<CertificateDTO> findByCriteria(Map<String, String> paramMap) {

        return certificateRepo.findByCriteria(paramMap)
                .stream()
                .map(certificateMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO update(CertificateDTO patch) throws ValidationException {
        CertificateDTO certificate = read(patch.getId());
        if (patch.getName() != null)
            certificate.setName(patch.getName());
        if (patch.getDuration() != null)
            certificate.setDuration(patch.getDuration());
        if (patch.getDescription() != null)
            certificate.setDescription(patch.getDescription());
        if (patch.getPrice() != null)
            certificate.setPrice(patch.getPrice());
        if (patch.getTags() != null)
            certificate.setTags(patch.getTags());
        certificateValidator.validate(certificate);
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateMapper.convertToDto(
                certificateRepo.update(certificateMapper.convertToEntity(certificate))
                        .orElseThrow(() -> (new EntityNotFoundException("certificate", 40401))));
    }

    @Override
    public CertificateDTO addTagToCertificate(long certificateId, TagDTO tag) {
        certificateRepo.addTagToCertificate(certificateId, tagMapper.convertToEntity(tag));
        return certificateMapper.convertToDto(
                certificateRepo.read(
                        certificateId).orElseThrow(() -> (new EntityNotFoundException("certificate", 40401))));
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, TagDTO tag) {
        certificateRepo.deleteTagFromCertificate(certificateId, tagMapper.convertToEntity(tag));
    }

    @Override
    public void delete(long id) {
        certificateRepo.delete(id);
    }

}
