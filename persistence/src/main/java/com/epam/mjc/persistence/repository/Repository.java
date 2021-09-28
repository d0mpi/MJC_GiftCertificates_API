package com.epam.mjc.persistence.repository;

import com.epam.mjc.model.DatabaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Repository<K extends Number, T extends DatabaseEntity> {
    T create(T entity);

    List<T> findAll();

    T findEntityById(K id);

    T update(T entity);

    void delete(K id);

    void delete(T entity);

    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
