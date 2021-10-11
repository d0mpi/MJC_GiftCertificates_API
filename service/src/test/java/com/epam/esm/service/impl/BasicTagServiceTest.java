package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.JdbcTagRepository;
import com.epam.esm.validation.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicTagServiceTest {
    private BasicTagService service;
    private TagMapper tagMapper;
    private JdbcTagRepository tagRepo;
    private TagValidator validator;

    @BeforeEach
    void setUp() {
        this.tagRepo = Mockito.mock(JdbcTagRepository.class);
        this.tagMapper = Mockito.mock(TagMapper.class);
        this.validator = Mockito.mock(TagValidator.class);
        this.service = new BasicTagService(tagRepo, tagMapper, validator);
    }

    @Test
    void create() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);
        Mockito.when(tagRepo.create(tag)).thenReturn(Optional.of(tag));

        assertEquals(tagDTO, service.create(tagDTO));
        Mockito.verify(tagRepo, Mockito.times(1)).create(tag);
        Mockito.verify(validator, Mockito.times(1)).validate(tagDTO);
    }

    @Test
    void read() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Optional<Tag> tagOptional = Optional.of(tag);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);
        Mockito.when(tagRepo.read(1L)).thenReturn(tagOptional);

        assertEquals(tagDTO, service.read(1L));
        Mockito.verify(tagRepo, Mockito.times(1)).read(1L);
    }

    @Test
    void findByCriteria() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);
        List<Tag> tagList = new LinkedList<>();
        tagList.add(tag);
        String query = "select * from tag";
        Mockito.when(tagRepo.findByCriteria(query)).thenReturn(tagList);
        assertEquals(tagList, service
                .findByCriteria(Collections.emptyMap())
                .stream().map(tagMapper::convertToEntity)
                .collect(Collectors.toList()));
        Mockito.verify(tagRepo, Mockito.times(1)).findByCriteria(query);
    }

    @Test
    void delete() {
        service.delete(1L);
        Mockito.verify(tagRepo, Mockito.times(1)).delete(1L);
    }
}