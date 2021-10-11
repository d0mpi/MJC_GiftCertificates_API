package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.searcher.TagQueryBuilder;
import com.epam.esm.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private final TagRepository repo;
    @Autowired
    private final TagMapper mapper;
    @Autowired
    private final TagValidator validator;

    @Override
    public TagDTO create(TagDTO tag) {
        validator.validate(tag);
        return mapper.convertToDto(
                repo.create(mapper.convertToEntity(tag)).orElseThrow(() -> (new EntityNotFoundException("tag", 40402))));
    }

    @Override
    public TagDTO read(long id) {
        return mapper.convertToDto(repo.read(id).orElseThrow(() -> (new EntityNotFoundException("tag", 40402))));
    }

    @Override
    public List<TagDTO> findByCriteria(Map<String, String> paramMap) {
        return repo.findByCriteria(paramMap)
                .stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

}
