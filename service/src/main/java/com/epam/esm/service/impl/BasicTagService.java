package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.searcher.TagSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicTagService implements TagService {
    @Autowired
    private final TagRepository repo;
    @Autowired
    private final TagMapper mapper;

    @Override
    public TagDTO create(TagDTO tag) {
        return mapper.convertToDto(repo.create(mapper.convertToEntity(tag)));
    }

    @Override
    public TagDTO read(long id) {
        return mapper.convertToDto(repo.read(id));
    }

    @Override
    public List<TagDTO> findByCriteria(Map<String, String> paramMap) {
        return repo.findByCriteria(new TagSearcher().getQuery(paramMap))
                .stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

    @Override
    public void delete(TagDTO tag) {
        repo.delete(tag.getId());
    }
}
