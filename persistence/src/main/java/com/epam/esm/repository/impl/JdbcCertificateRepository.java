package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.util.mapper.CertificateRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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
    public void addTagToCertificate(long certificateId, Tag tag) {
        Tag presentedTag = tagRepository.readByName(tag.getName()).orElse(null);
        if (presentedTag == null) {
            presentedTag = tagRepository.create(tag);
        } else {
            tag.setId(presentedTag.getId());
        }
        if(!read(certificateId).getTags().contains(tag)) {
            template.update(SQL_CREATE_CERTIFICATE_TAG, certificateId, presentedTag.getId());
        }
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, Tag tag) {
        template.update(SQL_DELETE_CERTIFICATE_TAG, certificateId, tag.getId());
    }

    @Override
    @Transactional
    public Certificate create(Certificate certificate) throws DAOException {
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
            List<Tag> tags = certificate.getTags();
            for (Tag tag : tags) {
                addTagToCertificate(certificate.getId(), tag);
            }
            return certificate;
        } else
            throw new DAOException("certificate.emptyKeyHolder", 40901);
    }

    @Override
    @Transactional
    public Certificate read(long id) throws DAOException {
        Certificate certificate = template.queryForStream(SQL_FIND_CERTIFICATE_BY_ID, certificateMapper::mapRowToObject, id)
                .distinct().findFirst().orElseThrow(() -> (new DAOException("certificate.notFound", 40401)));
        List<Tag> tags = tagRepository.findTagsByCertificateId(Objects.requireNonNull(certificate).getId());
        certificate.addTags(tags);
        return certificate;
    }

    @Override
    @Transactional
    public List<Certificate> findByCriteria(String sqlQuery) {
        List<Certificate> certificateList = template.query(sqlQuery, certificateMapper::mapRowToObject);
        for (Certificate certificate : certificateList) {
            certificate.addTags(tagRepository.findTagsByCertificateId(certificate.getId()));
        }
        return certificateList;
    }

    @Override
    @Transactional
    public Certificate update(Certificate certificate) {
        int updatedRowsNum = template.update(SQL_UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreate_date(),
                certificate.getLast_update_date(),
                certificate.getId());
        if (updatedRowsNum == 0)
            throw new DAOException("certificate.notFound", 40401);
        List<Tag> tags = certificate.getTags();
        for (Tag tag : tags) {
            addTagToCertificate(certificate.getId(), tag);
        }
        return certificate;
    }

    @Override
    public void delete(long id) {
        template.update(SQL_DELETE_CERTIFICATE, id);
    }
}
