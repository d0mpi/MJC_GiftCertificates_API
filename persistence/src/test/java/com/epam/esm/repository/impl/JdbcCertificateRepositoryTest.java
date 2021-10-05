package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.util.mapper.CertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JdbcCertificateRepositoryTest {
    private static JdbcCertificateRepository certificateRepository;
    private static JdbcTagRepository tagRepository;

    public static DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").build();
    }

    @BeforeAll
    static void init() {
        JdbcTemplate template = new JdbcTemplate(dataSource());
        certificateRepository = new JdbcCertificateRepository(
                template,
                new CertificateRowMapper(),
                new JdbcTagRepository(template, new TagRowMapper()));
    }

    @Test
    void findAllByCriteria_When_FindAll_Then_ReturnTen() {
        assertEquals(10, certificateRepository.findByCriteria("select * from certificate").size());
    }

    @Test
    void findAllByCriteria_When_FindByName_Ride_Then_ReturnOne() {
        assertEquals(1, certificateRepository.findByCriteria("select * from certificate where upper(name) LIKE upper('%ride%')").size());
    }

    @Test
    void findAllByCriteria_When_FindByDescription_Ride_Then_ReturnOne() {
        assertEquals(2, certificateRepository.findByCriteria("select * from certificate where upper(description) LIKE upper('%certificate%')").size());
    }

    @Test
    void findAllByCriteria_When_FindByTagName_ForOnePerson_Then_ReturnThree() {
        assertEquals(3, certificateRepository.findByCriteria("select certificate.id, certificate.name, certificate.description, certificate.price, certificate.duration, certificate.create_date, certificate.last_update_date" +
                "                from certificate" +
                "                         join certificate_tag" +
                "                              on certificate.id = certificate_tag.certificate_id" +
                "                         join tag" +
                "                              on certificate_tag.tag_id = tag.id" +
                "                where tag.name = 'for one person'").size());
    }

    @Test
    void read_When_IdEqualsTwo_Then_ReturnSecondCertificate(){
        Certificate certificate =  new Certificate(2, "Diving with dolphins",
                "The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.",
                new BigDecimal("220.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificate.addTags(List.of(new Tag(5, "entertainment")));
        assertEquals(certificate, certificateRepository.read(2));
    }

    @Test
    void read_When_ReadNonExistentCertificate_Then_ThrowDAOException(){
        assertThrows(DAOException.class,() -> certificateRepository.read(404));
    }

    @Test
    void create_When_CreteNewCertificate_Then_ShouldBeEqualsWithReadCertificateWithSpecifiedId() {
        Certificate certificate = new Certificate(11, "Diving with dolphins",
                "The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.",
                new BigDecimal("220.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificateRepository.create(certificate);
        assertEquals(certificate, certificateRepository.read(11));
    }

    @Test
    void update_When_UpdateAllTenthCertificateInfo_Then_ShouldBeEqualsWithReadCertificateWithSpecifiedId(){
        Certificate certificate = new Certificate(10, "Diving with dolphins",
                "The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.",
                new BigDecimal("220.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificate.addTags(List.of(new Tag(5, "entertainment")));
        certificateRepository.update(certificate);
        assertEquals(certificate, certificateRepository.read(10));
    }

    @Test
    void delete_When_DeleteCertificateWithIdNine_Then_ReadAndThrowDAOException(){
        certificateRepository.delete(9);
        assertThrows(DAOException.class, () -> certificateRepository.read(9));
    }

    @Test
    void addTagToCertificate_When_TagDoesNotExist_Then_ReadUpdatedCertificateWithNewTag(){
        certificateRepository.addTagToCertificate(8, new Tag("new tag"));
        Certificate certificate = new Certificate(8, "Vocal Mastery Course",
                "Want to get 100 karaoke points, perform at events, or sing beautifully in the shower? In one month, you will master the basic theoretical knowledge and acquire professional performance skills that will help you develop in the future.",
                new BigDecimal("94.0"), 31,
                LocalDateTime.parse("2021-06-12T10:34:09"),
                LocalDateTime.parse("2021-06-12T10:34:09"));
        certificate.addTags(List.of( new Tag(6, "for one person"), new Tag(8, "new tag"), new Tag(3, "training")));
        assertEquals(certificate, certificateRepository.read(8));
    }

    @Test
    void deleteTagFromCertificate_When_DeleteFromSevenCertificateFifthTag_Then_ReadCertificateWithoutTags(){
        certificateRepository.deleteTagFromCertificate(7, new Tag(5, "does not matter"));
        assertEquals(0, certificateRepository.read(7).getTags().size());
    }
}