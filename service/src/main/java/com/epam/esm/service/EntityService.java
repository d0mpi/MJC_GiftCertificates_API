package com.epam.esm.service;

import com.epam.esm.DTO.EntityDTO;
import com.epam.esm.exception.ValidationException;

import java.util.List;
import java.util.Map;

public interface EntityService<T extends EntityDTO> {

    T create(T entity) throws ValidationException;

    T read(long id);

    List<T> findByCriteria(Map<String, String> paramMap);

    void delete(long id);

    void delete(T entity);
}
