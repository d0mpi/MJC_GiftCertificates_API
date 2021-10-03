package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.searcher.CertificateSearcher;
import com.epam.esm.util.searcher.TagSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasicTagService implements TagService {
    private final TagRepository repo;
    private final TagMapper mapper;

    @Autowired
    public BasicTagService(TagRepository repo, TagMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

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
