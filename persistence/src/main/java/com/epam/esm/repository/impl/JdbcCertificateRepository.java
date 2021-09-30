package com.epam.esm.repository.impl;

import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@RequiredArgsConstructor
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
    private final CertificateMapper certificateMapper;

    @Autowired
    public JdbcCertificateRepository(JdbcTemplate template, CertificateMapper certificateMapper) {
        this.template = template;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public void addTagToCertificate(long certificateId, long tagId) {
        template.update(SQL_CREATE_CERTIFICATE_TAG, tagId, certificateId);
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, long tagId) {
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
        System.out.println("select all");
        List<Certificate> list = template.query(SQL_SELECT_ALL_CERTIFICATE, certificateMapper::mapRowToObject);
        System.out.println(list);
        return list;
    }

    @Override
    public Certificate findEntityById(long id) {
        return template.queryForObject(SQL_FIND_CERTIFICATE_BY_ID, certificateMapper::mapRowToObject, id);
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
    public void delete(long id) {
        template.update(SQL_DELETE_CERTIFICATE, id);
    }

    @Override
    public void delete(Certificate certificate) {
        delete(certificate.getId());
    }


}
