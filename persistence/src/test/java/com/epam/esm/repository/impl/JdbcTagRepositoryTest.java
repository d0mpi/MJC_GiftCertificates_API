package com.epam.esm.repository.impl;

import com.epam.esm.Tag;
import com.epam.esm.repository.TestPersistenceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
public class JdbcTagRepositoryTest {
    @Autowired
    private JdbcTagRepository tagRepository;

    @Test
    void create_When_CreteNewTag_Then_ShouldBeEqualsWithReadTagWithSpecifiedId() {
        Tag tag = new Tag(8L, "eight");
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
        tagRepository.delete(new Tag(3, null));
        assertNull(tagRepository.read(3).orElse(null));
    }

}