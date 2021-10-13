package com.epam.esm.repository.impl;

import com.epam.esm.Tag;
import com.epam.esm.util.mapper.TagRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JdbcTagRepositoryTest {
    private static JdbcTagRepository tagRepository;

    public static DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").build();
    }

    @BeforeAll
    static void init() {
        JdbcTemplate template = new JdbcTemplate(dataSource());
        tagRepository = new JdbcTagRepository(
                template,
                new TagRowMapper());
    }

    @Test
    void create_When_CreteNewTag_Then_ShouldBeEqualsWithReadTagWithSpecifiedId() {
        Tag tag = new Tag(8, "created tag");
        tagRepository.create(tag);
        assertEquals(tag, tagRepository.read(8).orElse(null));
    }

    @Test
    void read_When_IdEqualsTwo_Then_ReturnSecondCertificate() {
        Tag tag = new Tag(2, "sport");
        assertEquals(tag, tagRepository.read(2).orElse(null));
    }

    @Test
    void findTagsByCertificateId_When_CertificateIdEqualsEight_Then_ReturnTwo() {
        assertEquals(2, tagRepository.findTagsByCertificateId(8).size());
    }


    @Test
    void delete_When_DeleteTagWithIdThree_Then_ReadAndThrowDAOException() {
        tagRepository.delete(3);
        assertNull(tagRepository.read(3).orElse(null));
    }

}