package com.epam.esm.repository.impl;

import com.epam.esm.util.mapper.CertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class JdbcCertificateRepositoryTest {
    private static JdbcTemplate template;
    private static JdbcCertificateRepository certificateRepository;
    private static JdbcTagRepository tagRepository;

    public static DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").build();
    }

    @BeforeAll
    static void init() {
        template = new JdbcTemplate(dataSource());
        certificateRepository = new JdbcCertificateRepository(
                template,
                new CertificateRowMapper(),
                new JdbcTagRepository(template, new TagRowMapper()));
    }

    @Test
    void testFindAllCertificates() {
        assertEquals(certificateRepository.findByCriteria("select * from certificate").size(), 0);
    }
}