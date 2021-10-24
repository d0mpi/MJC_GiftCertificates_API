package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

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
//    private static final String SQL_CREATE_TAG = "insert into tag (name) values (?)";
//    private static final String SQL_FIND_ALL = "select id, name from tag";
//    private static final String SQL_FIND_TAG_BY_ID = "select id, name from tag where id = ?";
//    private static final String SQL_FIND_TAG_BY_NAME = "select id, name from tag where name = ?";
//    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "select t.id, t.name from tag t " +
//            "join certificate_tag ct " +
//            "on t.id = ct.tag_id " +
//            "where ct.certificate_id = ?";
//    private static final String SQL_DELETE_TAG = "delete from tag where id = ?";
//

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> create(Tag tag) {
        entityManager.persist(tag);
        return Optional.of(tag);
    }

    @Override
    public boolean exists(Tag tag) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(cb.count(root))
                .where(root.get("name").in(tag.getName()));
        return entityManager.createQuery(query)
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
    @SuppressWarnings("unchecked")
    public List<Tag> findTagsByCertificateId(long id) {
        return entityManager.createNativeQuery("select tag.id, tag.name from tag " +
                        "join certificate_tag ct " +
                        "on tag.id = ct.tag_id " +
                        "where ct.certificate_id = :certificateId", Tag.class)
                .setParameter("certificateId", id).getResultList();
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }
}
