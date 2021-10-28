package com.epam.esm.service.impl;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.EntityNotCreatedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.OrderIsAssociatedWithDatabaseEntity;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CertificateRepository certificateRepo;
    private final OrderRepository orderRepository;
    private final CertificateMapper certificateMapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateMapper.convertToDto(certificateRepo
                .create(certificateMapper.convertToEntity(certificate))
                .orElseThrow(() -> (new EntityNotCreatedException("message.not-created.certificate"))));
    }

    @Override
    @Transactional
    public CertificateDTO read(long id) {
        return certificateMapper.convertToDto(
                certificateRepo.read(id)
                        .orElseThrow(() -> (new EntityNotFoundException("message.not-found.certificate.id"))));
    }

    @Override
    @Transactional
    public PagedModel<CertificateDTO> findByCriteria(Map<String, String> paramMap, long page, long size) {
        List<CertificateDTO> certificateDTOList = certificateRepo.findByCriteria(paramMap, page, size)
                .stream()
                .map(certificateMapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, certificateRepo.getCount(paramMap));
        return PagedModel.of(certificateDTOList, metadata);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO patch) {
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
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateMapper.convertToDto(
                certificateRepo.update(certificateMapper.convertToEntity(certificate))
                        .orElseThrow(() -> (new EntityNotFoundException("message.not-found.certificate.id"))));
    }

    @Override
    @Transactional
    public CertificateDTO addTagToCertificate(long certificateId, TagDTO tag) {
        certificateRepo.addTagToCertificate(certificateId, tagMapper.convertToEntity(tag));
        return certificateMapper.convertToDto(
                certificateRepo.read(certificateId)
                        .orElseThrow(() -> (new EntityNotFoundException("message.not-found.certificate.id"))));
    }

    @Override
    @Transactional
    public void deleteTagFromCertificate(long certificateId, TagDTO tag) {
        certificateRepo.deleteTagFromCertificate(certificateId, tagMapper.convertToEntity(tag));
    }


    @Override
    @Transactional
    public void delete(long id) {
        Certificate certificate = certificateRepo.read(id).orElseThrow(() -> new EntityNotFoundException("message.not-found.certificate.id"));
        if (orderRepository.isCertificateAssociatedWithOrder(certificate)) {
            throw new OrderIsAssociatedWithDatabaseEntity("message.is-associated.certificate");
        }
            certificateRepo.delete(certificate);
    }

    @Override
    @Transactional
    public PagedModel<CertificateDTO> readAll(long page, long size) {
        List<CertificateDTO> certificateDTOList = certificateRepo.readAll(page, size)
                .stream()
                .map(certificateMapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, certificateRepo.getCount());
        return PagedModel.of(certificateDTOList, metadata);
    }

}
