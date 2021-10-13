package com.epam.esm.repository.impl;

import com.epam.esm.Tag;
import com.epam.esm.repository.TestPersistenceConfig;
import com.epam.esm.util.mapper.TagRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = TestPersistenceConfig.class)
class JdbcTagRepositoryTest {

    private static JdbcTagRepository tagRepository;

    @BeforeAll
    static void init(@Autowired JdbcTemplate template) {
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