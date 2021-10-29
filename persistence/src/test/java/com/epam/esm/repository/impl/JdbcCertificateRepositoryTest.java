package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.repository.TestPersistenceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
class JdbcCertificateRepositoryTest {
    @Autowired
    private JdbcCertificateRepository certificateRepository;

    @Test
    void findAllByCriteria_When_FindAll_Then_ReturnTen() {
        assertEquals(10, certificateRepository.findByCriteria(Collections.emptyMap(), 1, 10).size());
    }

    @Test
    void findAllByCriteria_When_FindByName_Ride_Then_ReturnOne() {
        assertEquals(1, certificateRepository.findByCriteria(Collections.singletonMap("name", "ride"), 1, 10).size());
    }

    @Test
    void findAllByCriteria_When_FindByDescription_Ride_Then_ReturnOne() {
        assertEquals(2, certificateRepository.findByCriteria(Collections.singletonMap("description", "certificate"), 1, 10).size());
    }

    @Test
    void findAllByCriteria_When_FindByTagName_ForOnePerson_Then_ReturnThree() {
        assertEquals(3, certificateRepository.findByCriteria(Collections.singletonMap("tag", "for one person"), 1, 10).size());
    }

    @Test
    void read_When_IdEqualsTwo_Then_ReturnSecondCertificate() {
        Certificate certificate = new Certificate(2, "Diving with dolphins",
                "The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.",
                new BigDecimal("220.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificate.addTags(Collections.singletonList(new Tag(5, "entertainment")));
        assertEquals(certificate, certificateRepository.read(2).orElse(null));
    }

    @Test
    void read_When_ReadNonExistentCertificate_Then_ThrowDAOException() {
        assertNull(certificateRepository.read(404).orElse(null));
    }

    @Test
    void create_When_CreteNewCertificate_Then_ShouldBeEqualsWithReadCertificateWithSpecifiedId() {
        Certificate certificate = new Certificate(11, "Diving with dolphins",
                "The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.",
                new BigDecimal("220.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificateRepository.create(certificate);
        assertEquals(certificate, certificateRepository.read(11).orElse(null));
    }

    @Test
    void update_When_UpdateAllTenthCertificateInfo_Then_ShouldBeEqualsWithReadCertificateWithSpecifiedId() {
        Certificate certificate = new Certificate(10, "Diving with dolphins",
                "The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.",
                new BigDecimal("220.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificateRepository.update(certificate);
        assertEquals(certificate, certificateRepository.read(10).orElse(null));
    }

    @Test
    void delete_When_DeleteCertificateWithIdNine_Then_ReadAndThrowDAOException() {
        certificateRepository.delete(new Certificate(9, null,
                null,
                null, 31,
                null,
                null));
        assertNull(certificateRepository.read(9).orElse(null));
    }

//    @Test
//    void addTagToCertificate_When_TagDoesNotExist_Then_ReadUpdatedCertificateWithNewTag() {
//        certificateRepository.addTagToCertificate(8, new Tag("new tag"));
//        Certificate certificate = new Certificate(8, "Vocal Mastery Course",
//                "Want to get 100 karaoke points, perform at events, or sing beautifully in the shower? In one month, you will master the basic theoretical knowledge and acquire professional performance skills that will help you develop in the future.",
//                new BigDecimal("94.0"), 31,
//                LocalDateTime.parse("2021-06-12T10:34:09"),
//                LocalDateTime.parse("2021-06-12T10:34:09"));
//        certificate.addTags(Arrays.asList(new Tag(6, "for one person"), new Tag(8, "new tag"), new Tag(3, "training")));
//        assertEquals(certificate, certificateRepository.read(8).orElse(null));
//    }
//
//    @Test
//    void deleteTagFromCertificate_When_DeleteFromSevenCertificateFifthTag_Then_ReadCertificateWithoutTags() {
//        certificateRepository.deleteTagFromCertificate(7, new Tag(5, "does not matter"));
//        assertEquals(0, Objects.requireNonNull(certificateRepository.read(7).orElse(null)).getTags().size());
//    }
}