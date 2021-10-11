package com.epam.esm.repository.impl;

import com.epam.esm.Tag;
import com.epam.esm.exception.CustomDataIntegrityViolationException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.mapper.TagRowMapper;
import com.epam.esm.util.searcher.CertificateQueryBuilder;
import com.epam.esm.util.searcher.TagQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the {@link TagRepository} class that uses JDBC to
 * interact with database.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see TagRepository
 * @see Repository
 */
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
    public Optional<Tag> create(Tag tag) {
        Tag existingTag = readByName(tag.getName()).orElse(null);
        if (existingTag != null)
            return Optional.of(existingTag);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            tag.setId(keyHolder.getKey().longValue());
            return Optional.of(tag);
        } else
            throw new CustomDataIntegrityViolationException("tag", 50002);
    }

    @Override
    public List<Tag> findByCriteria(Map<String, String> paramMap) {
        return template.query(TagQueryBuilder.init().getQuery(paramMap), tagMapper::mapRowToObject);
    }

    @Override
    public Optional<Tag> read(long id) {
        return template.queryForStream(SQL_FIND_TAG_BY_ID, tagMapper::mapRowToObject, id)
                .distinct().findFirst();
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
