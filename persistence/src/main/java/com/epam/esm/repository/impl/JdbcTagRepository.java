package com.epam.esm.repository.impl;

import com.epam.esm.mapper.TagMapper;
import com.epam.esm.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {


    private static final String SQL_CREATE_TAG = "insert into tag (name) values (?)";
    private static final String SQL_SELECT_ALL_TAG = "select id, name from tag";
    private static final String SQL_SELECT_TAG_BY_ID = "select id, name from tag where id = ?";
    private static final String SQL_UPDATE_TAG = "insert into tag (name) values (?)";
    private static final String SQL_DELETE_TAG = "delete from tag where id = ?";

    private final JdbcTemplate template;
    private final TagMapper tagMapper;

    @Autowired
    public JdbcTagRepository(JdbcTemplate template, TagMapper tagMapper) {
        this.template = template;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag create(Tag tag) {
        template.update(SQL_CREATE_TAG,
                tag.getName());
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return template.query(SQL_SELECT_ALL_TAG, tagMapper::mapRowToObject);
    }

    @Override
    public Tag findEntityById(long id) {
        return template.queryForObject(SQL_SELECT_TAG_BY_ID, tagMapper::mapRowToObject, id);
    }

    @Override
    public Tag update(Tag tag) {
        template.update(SQL_UPDATE_TAG,
                tag.getName());
        return tag;
    }

    @Override
    public void delete(long id) {
        template.update(SQL_DELETE_TAG, id);
    }

    @Override
    public void delete(Tag tag) {
        delete(tag.getId());
    }
    
}
