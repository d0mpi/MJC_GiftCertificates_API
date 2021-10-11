package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.CustomDataIntegrityViolationException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.util.mapper.CertificateRowMapper;
import com.epam.esm.util.searcher.CertificateQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;


/**
 * Implementation of the {@link CertificateRepository} class that uses JDBC to
 * interact with database.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see CertificateRepository
 * @see Repository
 */
@Repository
@RequiredArgsConstructor
public class JdbcCertificateRepository implements CertificateRepository {
    private static final String SQL_CREATE_CERTIFICATE = "insert into certificate (name, description, price," +
            "duration, create_date, last_update_date) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL_CERTIFICATE = "select id, name, description, price," +
            "duration, create_date, last_update_date from certificate";
    private static final String SQL_FIND_CERTIFICATE_BY_ID = "select id, name, description, price," +
            "duration, create_date, last_update_date from certificate where id = ?";
    private static final String SQL_UPDATE_CERTIFICATE = "update certificate set name = ?, description  = ?, price  = ?," +
            "duration = ?, create_date = ?, last_update_date = ? where id = ?";
    private static final String SQL_DELETE_CERTIFICATE = "delete from certificate where id = ?";
    private static final String SQL_CREATE_CERTIFICATE_TAG = "insert into certificate_tag (certificate_id, tag_id) values (?, ?)";
    private static final String SQL_DELETE_CERTIFICATE_TAG = "delete from certificate_tag where certificate_id = ? and tag_id = ?";

    @Autowired
    private final JdbcTemplate template;
    @Autowired
    private final CertificateRowMapper certificateMapper;
    @Autowired
    private final JdbcTagRepository tagRepository;

    @Override
    @Transactional
    public Optional<Tag> addTagToCertificate(long certificateId, Tag tag) {
        Tag presentedTag = tagRepository.readByName(tag.getName()).orElse(null);
        if (presentedTag == null) {
            presentedTag = tagRepository.create(tag).orElse(null);
            if (presentedTag == null)
                return Optional.empty();
        } else {
            tag.setId(presentedTag.getId());
        }
        if (!tagRepository.findTagsByCertificateId(certificateId).contains(tag)) {
            template.update(SQL_CREATE_CERTIFICATE_TAG, certificateId, presentedTag.getId());
        }
        return Optional.of(tag);
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, Tag tag) {
        template.update(SQL_DELETE_CERTIFICATE_TAG, certificateId, tag.getId());
    }

    @Override
    @Transactional
    public Optional<Certificate> create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setBigDecimal(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(certificate.getCreate_date()));
            ps.setTimestamp(6, Timestamp.valueOf(certificate.getLast_update_date()));
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            certificate.setId(keyHolder.getKey().longValue());
            Set<Tag> tags = certificate.getTags();
            for (Tag tag : tags) {
                addTagToCertificate(certificate.getId(), tag);
            }
            return Optional.of(certificate);
        } else
            throw new CustomDataIntegrityViolationException("certificate", 50001);
    }

    @Override
    @Transactional
    public Optional<Certificate> read(long id) {
        Certificate certificate = template.queryForStream(SQL_FIND_CERTIFICATE_BY_ID, certificateMapper::mapRowToObject, id)
                .distinct().findFirst().orElse(null);
        if (certificate != null) {
            List<Tag> tags = tagRepository.findTagsByCertificateId(certificate.getId());
            certificate.addTags(tags);
        }
        return Optional.ofNullable(certificate);
    }

    @Override
    @Transactional
    public List<Certificate> findByCriteria(Map<String, String> paramMap) {
        List<Certificate> certificateList = template.query(
                CertificateQueryBuilder.init().getQuery(paramMap), certificateMapper::mapRowToObject);
        for (Certificate certificate : certificateList) {
            certificate.addTags(tagRepository.findTagsByCertificateId(certificate.getId()));
        }
        return certificateList;
    }

    @Override
    @Transactional
    public Optional<Certificate> update(Certificate certificate) {
        int updatedRowsNum = template.update(SQL_UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreate_date(),
                certificate.getLast_update_date(),
                certificate.getId());
        if (updatedRowsNum == 0)
            return Optional.empty();
        Set<Tag> tags = certificate.getTags();
        for (Tag tag : tags) {
            addTagToCertificate(certificate.getId(), tag);
        }
        return Optional.of(certificate);
    }

    @Override
    public void delete(long id) {
        template.update(SQL_DELETE_CERTIFICATE, id);
    }
}
