package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> create(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> read(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> readAll(int page, int limit) {
        return null;
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
