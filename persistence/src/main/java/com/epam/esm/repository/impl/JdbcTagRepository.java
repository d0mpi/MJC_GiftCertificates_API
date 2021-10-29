package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;
import com.epam.esm.User;
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
    public Optional<Tag> update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tag> readByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("select t from Tag t where t.name = :name", Tag.class)
                    .setParameter("name", name).setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> getMostWidelyUsedTag(User user) {
//        return Optional.of((Tag) entityManager.createQuery("select tag.id, tag.name, count(tag.id) as countt, sum(orders.cost) as summ " +
//                        "from Tag tag " +
//                        "join tag.certificates certificate " +
//                        "join certificate.orders orders" +
//                        "join orders.user tag " +
//                        "where u.id = :user_id " +
//                        "group by tag.id " +
//                        "order by countt desc, summ desc")
//                .setParameter("user_id", user.getId())
//                .setMaxResults(1)
//                .getSingleResult());

        return Optional.of((Tag) entityManager.createNativeQuery("select tag.id, tag.name, count(tag.id) as count, sum(o.cost) as sum " +
                        "from tag " +
                        "         inner join certificate_tag ct " +
                        "                    on tag.id = ct.tag_id " +
                        "         inner join certificate c " +
                        "                    on ct.certificate_id = c.id " +
                        "         inner join orders o " +
                        "                    on c.id = o.certificate_id " +
                        "         inner join user u " +
                        "                    on o.user_id = u.id " +
                        "where u.id = :user_id " +
                        "group by tag.id " +
                        "order by count desc, sum desc limit 1", Tag.class)
                .setParameter("user_id", user.getId())
                .getSingleResult());
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
