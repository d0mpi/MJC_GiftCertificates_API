package com.epam.esm.repository;

import com.epam.esm.DatabaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Provides CRD operations on {@link DatabaseEntity} required to interact with database.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see CertificateRepository, TagRepository
 */
public interface Repository<T extends DatabaseEntity> {
    /**
     * Saves object to rhe database
     *
     * @param entity the object to be saved to the database
     * @return saved object
     */
    Optional<T> create(T entity);

    /**
     * Finds object in rhe database. Uses sqlQuery to find entities.
     *
     * @param sqlQuery the object to be saved to the database
     * @return list of found objects
     */
    List<T> findByCriteria(String sqlQuery);

    /**
     * Reads object from rhe database
     *
     * @param id id of the object to be read from the database
     * @return read object
     */
    Optional<T> read(long id);

    /**
     * Deletes object with the specified id from rhe database
     *
     * @param id id of the object to be deleted
     */
    void delete(long id);
}
