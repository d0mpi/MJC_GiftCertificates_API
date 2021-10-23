package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.JdbcTagRepository;
import com.epam.esm.validation.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BasicTagServiceTest {
    @Mock
    private TagMapper tagMapper;
    @Mock
    private JdbcTagRepository repo;
    @Mock
    private TagValidator validator;
    private BasicTagService service;

    @BeforeEach
    void init() {
        service = new BasicTagService(repo, tagMapper, validator);
    }

    @Test
    void create_When_CreateTagDTOFromService_Should_InvokeCreateTagMethodInRepoAndConvertToDTO() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);
        Mockito.when(repo.create(tag)).thenReturn(Optional.of(tag));
        assertEquals(tagDTO, service.create(tagDTO));
        Mockito.verify(repo, Mockito.times(1)).create(tag);
        Mockito.verify(validator, Mockito.times(1)).validate(tagDTO);
    }

    @Test
    void read_When_ReadTagByIdFromService_Should_InvokeReadTagMethodInRepoAndConvertToDTO() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Optional<Tag> tagOptional = Optional.of(tag);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(repo.read(1L)).thenReturn(tagOptional);

        assertEquals(tagDTO, service.read(1L));
        Mockito.verify(repo, Mockito.times(1)).read(1L);
    }

    @Test
    void findByCriteria_When_FindWithoutParams_Should_InvokeFindTagsMethodAndConvertToDTO() {
        Tag tag = Mockito.mock(Tag.class);
        TagDTO tagDTO = Mockito.mock(TagDTO.class);
        Mockito.when(tagMapper.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(tagMapper.convertToEntity(tagDTO)).thenReturn(tag);
        List<Tag> tagList = new LinkedList<>();
        tagList.add(tag);
        Mockito.when(repo.readAll(0, 0)).thenReturn(tagList);
        assertEquals(tagList, service
                .readAll(0, 0).getContent()
                .stream().map(tagMapper::convertToEntity)
                .collect(Collectors.toList()));
        Mockito.verify(repo, Mockito.times(1)).readAll(0, 0);
    }

    @Test
    void delete_Should_InvokeDeleteMethodInRepo() {
        service.delete(1L);
        Mockito.verify(repo, Mockito.times(1)).delete(new Tag(1L, null));
    }
}