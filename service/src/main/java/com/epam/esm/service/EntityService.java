package com.epam.esm.service;

import com.epam.esm.Certificate;
import com.epam.esm.DatabaseEntity;

import java.util.List;
import java.util.Map;

public interface EntityService<T extends DatabaseEntity> {

    T create(T entity);

    T read(long id);

    List<T> findByCriteria(Map<String, String> paramMap);

    void delete(long id);

    void delete(T entity);
}
