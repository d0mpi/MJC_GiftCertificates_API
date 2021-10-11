package com.epam.esm.service.impl;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.JdbcCertificateRepository;
import com.epam.esm.validation.CertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BasicCertificateServiceTest {

    private BasicCertificateService service;
    private CertificateMapper certificateMapper;
    private TagMapper tagMapper;
    private JdbcCertificateRepository certificateRepo;
    private CertificateValidator validator;

    @BeforeEach
    void setUp() {
        this.certificateRepo = Mockito.mock(JdbcCertificateRepository.class);
        this.certificateMapper = Mockito.mock(CertificateMapper.class);
        this.tagMapper = Mockito.mock(TagMapper.class);
        this.validator = Mockito.mock(CertificateValidator.class);
        this.service = new BasicCertificateService(certificateRepo, certificateMapper, tagMapper,
                validator);
    }

    @Test
    void createNewCertificate() {
        Certificate certificate = Mockito.mock(Certificate.class);
        CertificateDTO certificateDTO = Mockito.mock(CertificateDTO.class);
        Mockito.when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateMapper.convertToEntity(certificateDTO)).thenReturn(certificate);
        Mockito.when(certificateRepo.create(certificate)).thenReturn(certificate);

        assertEquals(certificateDTO, service.create(certificateDTO));
        Mockito.verify(certificateRepo, Mockito.times(1)).create(certificate);
        Mockito.verify(validator, Mockito.times(1)).validate(certificateDTO);
    }

    @Test
    void readCertificateById() {
        Certificate certificate = Mockito.mock(Certificate.class);
        Mockito.when(certificateRepo.read(1L)).thenReturn(certificate);

        assertEquals(certificateMapper.convertToDto(certificate), service.read(1L));
        Mockito.verify(certificateRepo, Mockito.times(1)).read(1L);
    }

    @Test
    void findByCriteria() {
        Certificate certificate = Mockito.mock(Certificate.class);
        CertificateDTO certificateDTO = Mockito.mock(CertificateDTO.class);
        Mockito.when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateMapper.convertToEntity(certificateDTO)).thenReturn(certificate);
        List<Certificate> certificateList = new LinkedList<>();
        certificateList.add(certificate);
        String query = "select certificate.id," +
                " certificate.name," +
                " certificate.description," +
                " certificate.price," +
                " certificate.duration," +
                " certificate.create_date," +
                " certificate.last_update_date " +
                " from certificate ";
        Mockito.when(certificateRepo.findByCriteria(query)).thenReturn(certificateList);
        assertEquals(certificateList, service
                .findByCriteria(Collections.emptyMap())
                .stream().map(certificateMapper::convertToEntity)
                .collect(Collectors.toList()));
        Mockito.verify(certificateRepo, Mockito.times(1)).findByCriteria(query);

    }

    @Test
    void update() {
        Certificate certificate = Mockito.mock(Certificate.class);
        CertificateDTO certificateDTO = Mockito.mock(CertificateDTO.class);
        Mockito.when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateMapper.convertToEntity(certificateDTO)).thenReturn(certificate);
        Mockito.when(certificateRepo.update(certificate)).thenReturn(certificate);
        service.update(certificateDTO);
        Mockito.verify(certificateRepo, Mockito.times(1)).update(certificate);
    }

    @Test
    void addTagToCertificate() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);

        service.addTagToCertificate(1L, tagDTO);
        Mockito.verify(certificateRepo, Mockito.times(1)).addTagToCertificate(1L, tag);
        Mockito.verify(certificateRepo, Mockito.times(1)).read(1L);
    }

    @Test
    void deleteTagFromCertificate() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);

        service.deleteTagFromCertificate(1L, tagDTO);
        Mockito.verify(certificateRepo, Mockito.times(1)).deleteTagFromCertificate(1L, tag);
    }

    @Test
    void delete() {
        service.delete(1L);
        Mockito.verify(certificateRepo, Mockito.times(1)).delete(1L);
    }
}