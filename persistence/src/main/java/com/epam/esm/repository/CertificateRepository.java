package com.epam.esm.repository;

import com.epam.esm.Certificate;
import com.epam.esm.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Provides CRUD operations on {@link Certificate} required to interact with database.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see Repository
 */
public interface CertificateRepository extends Repository<Certificate> {
    /**
     * Creates new tag if tag with the specified id does not exist
     * and adds entry to the table linking {@link Certificate} and tag.
     *
     * @param certificateId id of the {@link Certificate} to which the tag should be added
     * @param tag           {@link Tag} which should be added to the {@link Certificate} with the specified id
     */
    Optional<Tag> addTagToCertificate(long certificateId, Tag tag);

    /**
     * Delete entry from the table linking {@link Certificate} and tag.
     *
     * @param certificateId id of the {@link Certificate} from wich the tag should be deleted
     * @param tag           {@link Tag} which should be deleted from the {@link Certificate} with the specified id
     */
    void deleteTagFromCertificate(long certificateId, Tag tag);

    /**
     * Finds object in rhe database. Uses sqlQuery to find entities.
     *
     * @param paramMap map with parameters
     * @return list of found objects
     */
    List<Certificate> findByCriteria(Map<String, String> paramMap, long page, long size);

    long getCount(Map<String, String> paramMap);
}
