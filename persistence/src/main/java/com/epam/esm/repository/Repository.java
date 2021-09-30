package com.epam.esm.repository;

import com.epam.esm.DatabaseEntity;

import java.util.List;

public interface Repository<T extends DatabaseEntity> {
    T create(T entity);

    List<T> findAll();

    T findEntityById(long id);

    T update(T entity);

    void delete(long id);

    void delete(T entity);
}
