package com.epam.mjc.persistence.repository.impl;

import com.epam.mjc.model.Tag;
import com.epam.mjc.persistence.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcTagRepository implements TagRepository {


    private static final String SQL_CREATE_TAG = "insert into tag (name) values (?)";
    private static final String SQL_SELECT_ALL_TAG = "select id, name from tag";
    private static final String SQL_SELECT_TAG_BY_ID = "select id, name from tag where id = ?";
    private static final String SQL_UPDATE_TAG = "insert into tag (name) values (?)";
    private static final String SQL_DELETE_TAG = "delete from tag where id = ?";
    private final JdbcTemplate template;

    @Autowired
    public JdbcTagRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Tag create(Tag tag) {
        template.update(SQL_CREATE_TAG,
                tag.getName());
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return template.query(SQL_SELECT_ALL_TAG, this::mapRow);
    }

    @Override
    public Tag findEntityById(Integer id) {
        return template.queryForObject(SQL_SELECT_TAG_BY_ID, this::mapRow, id);
    }

    @Override
    public Tag update(Tag tag) {
        template.update(SQL_UPDATE_TAG,
                tag.getName());
        return tag;
    }

    @Override
    public void delete(Integer id) {
        template.update(SQL_DELETE_TAG, id);
    }

    @Override
    public void delete(Tag tag) {
        delete(tag.getId());
    }

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return  new Tag(rs.getInt("id"),
                rs.getString("name"));
    }
}
