package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.util.searcher.CertificateQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
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
    @PersistenceContext
    private EntityManager entityManager;
    private final JdbcTagRepository tagRepository;


    @Override
    public Optional<Tag> addTagToCertificate(long certificateId, Tag tag) {
        Certificate certificate = this.read(certificateId).orElse(null);
        if (certificate == null) {
            return Optional.empty();
        }
        Set<Tag> tags = certificate.getTags();
        Tag createdTag = tagRepository.readByName(tag.getName())
                .orElseGet(() -> tagRepository.create(tag).orElse(null));
        tags.add(createdTag);
        entityManager.merge(certificate);
        return Optional.of(tag);
    }

    @Override
    public void deleteTagFromCertificate(long certificateId, Tag tag) {
        Certificate certificate = this.read(certificateId).orElse(null);
        if (certificate == null) {
            throw new EntityNotFoundException();
        }
        Set<Tag> tags = certificate.getTags();
        Tag existedTag = tagRepository.readByName(tag.getName()).orElseThrow(EntityNotFoundException::new);
        if (tags.contains(existedTag)) {
            tags.remove(existedTag);
        } else {
            throw new EntityNotFoundException();
        }
        System.out.println(tag);
        System.out.println(existedTag);
        entityManager.merge(certificate);
    }

    @Override
    public Optional<Certificate> create(Certificate certificate) {
        setExistedTagsId(certificate);
        entityManager.persist(certificate);
        return Optional.of(certificate);
    }

    @Override
    public Optional<Certificate> read(long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Certificate> readAll(long page, long size) {
        return entityManager.createQuery("select c from Certificate c")
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();
    }

    @Override
    public List<Certificate> findByCriteria(Map<String, String> paramMap, long page, long size) {
        List<Certificate> certificateList = CertificateQueryBuilder
                .init()
                .getQuery(paramMap, entityManager)
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();
        for (Certificate certificate : certificateList) {
            Set<Tag> tags = tagRepository.findTagsByCertificateId(certificate.getId());
            certificate.addTags(new LinkedList<>(tags));
        }
        return certificateList;
    }

    @Override
    public long getCount(Map<String, String> paramMap) {
        return CertificateQueryBuilder
                .init()
                .getQuery(paramMap, entityManager)
                .getResultList().size();
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        setExistedTagsId(certificate);
        return Optional.of(entityManager.merge(certificate));
    }

    private void setExistedTagsId(Certificate certificate) {
        Set<Tag> tags = new HashSet<>();
        for (Tag tag : certificate.getTags()) {
            if (tagRepository.exists(tag)) {
                tag = tagRepository.readByName(tag.getName()).get();
            }
            tags.add(tag);
        }
        certificate.setTags(tags);
    }

    @Override
    public long getCount() {
        return (long) entityManager.createQuery("select count (c) from Certificate c").getSingleResult();
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }
}
