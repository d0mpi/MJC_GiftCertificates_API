package com.epam.esm.repository;

import com.epam.esm.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Provides CRD operations on {@link Tag} required to interact with database.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see Repository
 */
public interface TagRepository extends Repository<Tag> {
    /**
     * Finds list of tags belonging to the certificate with the specifies id
     *
     * @param id id of the certificate whoose tags need to be found
     * @return list of tags belonging to the certificate with the specifies id
     */
    Set<Tag> findTagsByCertificateId(long id);

    boolean exists(Tag tag);

    /**
     * Reads tag from the database by name
     *
     * @param name name of the tag to be read
     * @return {@link Optional} with {@link Tag} received from the database, contains null if
     * the {@link Tag} with the specified id does not exist in the databse
     */
    Optional<Tag> readByName(String name);

    Tag getMostWidelyUsedTag(long userId);
}
