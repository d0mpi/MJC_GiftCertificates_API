package com.epam.mjc.persistence.repository.impl;

import com.epam.mjc.model.Certificate;
import com.epam.mjc.persistence.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
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
    private static final String SQL_CREATE_CERTIFICATE_TAG = "insert into certificate_tag (tag_id, certificate_id) values (?, ?)";
    private static final String SQL_DELETE_CERTIFICATE_TAG = "delete from certificate_tag where tag_id = ? and certificate_id = ?";
    private final JdbcTemplate template;

    @Autowired
    public JdbcCertificateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void addTagToCertificate(int certificateId, int tagId) {
        template.update(SQL_CREATE_CERTIFICATE_TAG, tagId, certificateId);
    }

    @Override
    public void deleteTagFromCertificate(int certificateId, int tagId) {
        template.update(SQL_DELETE_CERTIFICATE_TAG, tagId, certificateId);
    }

    @Override
    public Certificate create(Certificate certificate) {
        template.update(SQL_CREATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreate_date(),
                certificate.getLast_update_date());
        return certificate;
    }

    @Override
    public List<Certificate> findAll() {
        return template.query(SQL_SELECT_ALL_CERTIFICATE, this::mapRow);
    }

    @Override
    public Certificate findEntityById(Integer id) {
        return template.queryForObject(SQL_FIND_CERTIFICATE_BY_ID, this::mapRow, id);
    }


    @Override
    public Certificate update(Certificate certificate) {
        template.update(SQL_UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreate_date(),
                certificate.getLast_update_date(),
                certificate.getId());
        return certificate;
    }

    @Override
    public void delete(Integer id) {
        template.update(SQL_DELETE_CERTIFICATE, id);
    }

    @Override
    public void delete(Certificate certificate) {
        delete(certificate.getId());
    }

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Certificate(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("duration"),
                rs.getTimestamp("create_date").toLocalDateTime(),
                rs.getTimestamp("last_update_date").toLocalDateTime());
    }
}
