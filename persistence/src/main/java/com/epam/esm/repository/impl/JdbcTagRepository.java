package com.epam.esm.repository.impl;

import com.epam.esm.exception.DAOException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.mapper.TagRowMapper;
import com.epam.esm.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {
    private static final String SQL_CREATE_TAG = "insert into tag (name) values (?)";
    private static final String SQL_FIND_TAG_BY_ID = "select id, name from tag where id = ?";
    private static final String SQL_FIND_TAG_BY_NAME = "select id, name from tag where name = ?";
    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "select t.id, t.name from tag t " +
            "join certificate_tag ct " +
            "on t.id = ct.tag_id " +
            "where ct.certificate_id = ?";

    private static final String SQL_DELETE_TAG = "delete from tag where id = ?";

    @Autowired
    private final JdbcTemplate template;
    @Autowired
    private final TagRowMapper tagMapper;

    @Override
    public Tag create(Tag tag) throws DAOException {
        Tag existingTag = readByName(tag.getName()).orElse(null);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if(existingTag != null)
            return existingTag;

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            tag.setId(keyHolder.getKey().longValue());
            return tag;
        } else
            throw new DAOException("tag.emptyKeyHolder", 40902);

    }

    @Override
    public List<Tag> findByCriteria(String sqlQuery) {
        return template.query(sqlQuery, tagMapper::mapRowToObject);
    }

    @Override
    public Tag read(long id) throws DAOException {
        return template.queryForStream(SQL_FIND_TAG_BY_ID, tagMapper::mapRowToObject, id)
                .distinct().findFirst().orElseThrow(() -> (new DAOException("tag.notFound", 40402)));
    }

    @Override
    public Optional<Tag> readByName(String name) {
        return template.queryForStream(SQL_FIND_TAG_BY_NAME, tagMapper::mapRowToObject, name)
                .findFirst();
    }

    @Override
    public List<Tag> findTagsByCertificateId(long id) {
        return template.query(SQL_SELECT_TAGS_BY_CERTIFICATE_ID, tagMapper::mapRowToObject, id);
    }

    @Override
    public void delete(long id) {
        template.update(SQL_DELETE_TAG, id);
    }
}
