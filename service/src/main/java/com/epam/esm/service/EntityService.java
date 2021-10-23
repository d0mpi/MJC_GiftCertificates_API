package com.epam.esm.service;

import com.epam.esm.exception.ValidationException;
import org.springframework.hateoas.PagedModel;

/**
 * Provides database entity info transfer between web and persistence module,
 * required to convert entity info from DTO to entity that the repository
 * operates on and vice-versa. Is necessary for performing
 * intermediate actions on entity info (verifying, updating data and etc.)
 *
 * @param <T> DTO class
 */
public interface EntityService<T> {
    /**
     * Provides a link between web and persistence module.
     * Responsible for converting DTO to entity that
     * persistence module operates on and for performing
     * intermediate actions on the received entity
     *
     * @param entity transmitted entity
     * @return converted to DTO entity received from persistence
     * @throws ValidationException if the transmitted information is not valid
     */
    T create(T entity);

    /**
     * Provides a link between web and persistence module.
     * Gets entity by its id.
     *
     * @param id id of the certificate to be found
     * @return converted to DTO entity received from persistence
     */
    T read(long id);


    /**
     * Provides a link between web and persistence module.
     * Deletes entity from database
     *
     * @param id id of the entity to be deleted from the database
     */
    void delete(long id);

    PagedModel<T> readAll(long page, long size);
}
