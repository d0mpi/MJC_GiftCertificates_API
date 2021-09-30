package com.epam.esm.service.impl;

import com.epam.esm.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BasicTagService implements TagService {
    private final TagRepository repo;

    @Autowired
    public BasicTagService(TagRepository repo) {
        this.repo = repo;
    }

    @Override
    public Tag create(Tag tag) {
        return repo.create(tag);
    }

    @Override
    public Tag read(long id) {
        return repo.read(id);
    }

    @Override
    public List<Tag> findByCriteria(Map<String, String> paramMap) {
        return null;
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

    @Override
    public void delete(Tag tag) {
        repo.delete(tag.getId());
    }
}
