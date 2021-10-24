package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of the {@link TagService} class.
 * Transfer data to {@link com.epam.esm.repository.impl.JdbcTagRepository}
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see TagService
 */
@Service
@RequiredArgsConstructor
public class BasicTagService implements TagService {
    private final TagRepository repo;
    private final TagMapper mapper;
    private final TagValidator validator;

    @Override
    @Transactional
    public TagDTO create(TagDTO tag) {
        if (repo.exists(mapper.convertToEntity(tag))) {
            throw new EntityExistsException("already exists");
        }
        validator.validate(tag);
        return mapper.convertToDto(
                repo.create(mapper.convertToEntity(tag)).orElseThrow(() -> (new EntityNotFoundException("tag", 40402))));
    }

    @Override
    @Transactional
    public TagDTO read(long id) {
        return mapper.convertToDto(repo.read(id).orElseThrow(() -> (new EntityNotFoundException("tag", 40402))));
    }

    @Override
    @Transactional
    public PagedModel<TagDTO> readAll(long page, long size) {
        List<TagDTO> tagDTOList = repo.readAll(page, size)
                .stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, repo.getCount());
        return PagedModel.of(tagDTOList, metadata);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Tag tag = repo.read(id).orElseThrow(() -> (new EntityNotFoundException("tag", 40402)));
        repo.delete(tag);
    }

    @Override
    @Transactional
    public TagDTO getMostWidelyUsedTag(long userId) {
        return null;
    }
}
