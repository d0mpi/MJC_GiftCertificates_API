package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTagRepository implements TagRepository {
    private static final String SQL_CREATE_TAG = "insert into tag (name) values (?)";
    private static final String SQL_FIND_TAG_BY_ID = "select id, name from tag where id = ?";
    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "select id, name from tag " +
            "join certificate_tag on tag.id = certificate_tag.tag_id " +
            "where certificate_id = ?";
//    private static final String SQL_UPDATE_TAG = "insert into tag (name) values (?)";
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
    public List<Tag> findByCriteria(String sqlQuery) {
        return template.query(sqlQuery, tagMapper::mapRowToObject);
    }

    @Override
    public Tag read(long id) {
        return template.queryForStream(SQL_FIND_TAG_BY_ID, tagMapper::mapRowToObject, id)
                .distinct().findFirst().orElse(null);
    }

    @Override
    public List<Tag> findTagsByCertificateId(long id) {
        return template.query(SQL_SELECT_TAGS_BY_CERTIFICATE_ID, tagMapper::mapRowToObject);
    }

    @Override
    public void delete(long id) {
        template.update(SQL_DELETE_TAG, id);
    }
}
