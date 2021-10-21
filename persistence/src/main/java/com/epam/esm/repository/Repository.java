package com.epam.esm.repository;

import com.epam.esm.Certificate;
import com.epam.esm.DatabaseEntity;

import java.util.List;
import java.util.Map;
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
     * @param paramMap map with parameters
     * @return list of found objects
     */
    List<T> findByCriteria(Map<String, String> paramMap);

    /**
     * Reads object from rhe database
     *
     * @param id id of the object to be read from the database
     * @return read object
     */
    Optional<T> read(long id);


    List<T> readAll(int page, int limit);

    /**
     * Updates info about the specified {@link Certificate} in the database
     *
     * @param certificate {@link Certificate} instance containing info to be updated
     * @return updated {@link Certificate}
     */
    Optional<Certificate> update(Certificate certificate);

    /**
     * Deletes object with the specified id from rhe database
     *
     * @param id id of the object to be deleted
     */
    void delete(long id);
}
