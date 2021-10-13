package com.epam.esm.service.impl;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.JdbcCertificateRepository;
import com.epam.esm.validation.CertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BasicCertificateServiceTest {

    @Mock
    private BasicCertificateService service;
    @Mock
    private CertificateMapper certificateMapper;
    @Mock
    private TagMapper tagMapper;
    @Mock
    private JdbcCertificateRepository certificateRepo;
    @Mock
    private CertificateValidator validator;


    @BeforeEach
    void setUp(@Mock TagRepository tagRepository) {
        this.service = new BasicCertificateService(certificateRepo, tagRepository, certificateMapper, tagMapper,
                validator);
    }

    @Test
    void createNewCertificate() {
        Certificate certificate = Mockito.mock(Certificate.class);
        CertificateDTO certificateDTO = Mockito.mock(CertificateDTO.class);
        Mockito.when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateMapper.convertToEntity(certificateDTO)).thenReturn(certificate);
        Mockito.when(certificateRepo.create(certificate)).thenReturn(Optional.of(certificate));

        assertEquals(certificateDTO, service.create(certificateDTO));
        Mockito.verify(certificateRepo, Mockito.times(1)).create(certificate);
        Mockito.verify(validator, Mockito.times(1)).validate(certificateDTO);
    }

    @Test
    void readCertificateById() {
        Certificate certificate = Mockito.mock(Certificate.class);
        Optional<Certificate> certificateOptional = Optional.of(certificate);
        Mockito.when(certificateRepo.read(1L)).thenReturn(certificateOptional);

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
        Mockito.when(certificateRepo.findByCriteria(Collections.emptyMap())).thenReturn(certificateList);
        assertEquals(certificateList, service
                .findByCriteria(Collections.emptyMap())
                .stream().map(certificateMapper::convertToEntity)
                .collect(Collectors.toList()));
        Mockito.verify(certificateRepo, Mockito.times(1)).findByCriteria(Collections.emptyMap());

    }

    @Test
    void update() {
        Certificate certificate = Mockito.mock(Certificate.class);
        CertificateDTO certificateDTO = Mockito.mock(CertificateDTO.class);
        Mockito.when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateMapper.convertToEntity(certificateDTO)).thenReturn(certificate);
        Mockito.when(certificateMapper.convertToEntity(certificateDTO)).thenReturn(certificate);
        Mockito.when(certificateRepo.update(certificate)).thenReturn(Optional.of(certificate));
        Mockito.when(certificateRepo.read(certificateDTO.getId())).thenReturn(Optional.of(certificate));
        service.update(certificateDTO);
        Mockito.verify(certificateRepo, Mockito.times(1)).update(certificate);
    }

    @Test
    void addTagToCertificate() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);
        Mockito.when(certificateRepo.read(1L)).thenReturn(Optional.of(new Certificate()));
        service.addTagToCertificate(1L, tagDTO);
        Mockito.verify(certificateRepo, Mockito.times(1)).addTagToCertificate(1L, tag);
        Mockito.verify(certificateRepo, Mockito.times(1)).read(1L);
    }

    @Test
    void deleteTagFromCertificate() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
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