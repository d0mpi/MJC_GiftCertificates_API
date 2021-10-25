package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the {@link TagRepository} class that uses JDBC to
 * interact with database.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see TagRepository
 * @see Repository
 */
@Repository
@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> create(Tag tag) {
        entityManager.persist(tag);
        return Optional.of(tag);
    }

    @Override
    public boolean exists(Tag tag) {
        return entityManager.createQuery("select count(t) from Tag t where t.name= :name", Long.class)
                .setParameter("name", tag.getName())
                .getSingleResult() != 0;
    }

    @Override
    public Optional<Tag> read(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> readAll(long page, long size) {
        return (List<Tag>) entityManager.createQuery("select t from Tag t")
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tag> readByName(String name) {
        try {
            return Optional.of((Tag) entityManager.createQuery("select t from Tag t where t.name = :name")
                    .setParameter("name", name).setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Tag getMostWidelyUsedTag(long userId) {
        return null;
    }

    @Override
    public long getCount() {
        return (long) entityManager.createQuery("SELECT COUNT(t) FROM Tag t").getSingleResult();
    }

    @Override
    public Set<Tag> findTagsByCertificateId(long id) {
        Certificate certificate = (Certificate) entityManager.createQuery("select c from Certificate c where c.id = :id")
                .setParameter("id", id)
                .setMaxResults(1)
                .getSingleResult();
        return certificate.getTags();
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }
}
