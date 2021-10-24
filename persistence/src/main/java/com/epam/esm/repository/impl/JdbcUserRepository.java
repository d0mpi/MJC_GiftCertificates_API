package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> create(User user) {
        entityManager.persist(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> read(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> readAll(long page, long size) {
        return entityManager.createQuery("select u from User u")
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCount() {
        return (long) entityManager.createQuery("select count (u) from User u").getSingleResult();
    }

    @Override
    public boolean exists(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(cb.count(root))
                .where(root.get("email").in(user.getEmail()));
        return entityManager.createQuery(query)
                .getSingleResult() != 0;
    }
}
