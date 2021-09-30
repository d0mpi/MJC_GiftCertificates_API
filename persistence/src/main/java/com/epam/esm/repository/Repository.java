package com.epam.esm.repository;

import com.epam.esm.Certificate;
import com.epam.esm.DatabaseEntity;

import java.util.List;

public interface Repository<T extends DatabaseEntity> {
    T create(T entity);

    List<T> findByCriteria(String sqlQuery);

    T read(long id);

    void delete(long id);
}
