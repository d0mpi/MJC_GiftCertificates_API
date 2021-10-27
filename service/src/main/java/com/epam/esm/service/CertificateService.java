package com.epam.esm.service;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import org.springframework.hateoas.PagedModel;

import java.util.Map;

/**
 * Provides certificate info transfer between web and persistence module,
 * Required to convert certificate info from {@link CertificateDTO}
 * to {@link com.epam.esm.Certificate} and vice-versa. Is necessary for performing
 * intermediate actions on Certificate info (verifying, updating data etc.)
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see com.epam.esm.repository.CertificateRepository
 */
public interface CertificateService extends EntityService<CertificateDTO> {
    /**
     * Provides a link between web and persistence module.
     * Responsible for updating received entity.
     *
     * @param entity entity containing information about certificate
     *               to be updated
     * @return updated {@link CertificateDTO}
     */
    CertificateDTO update(CertificateDTO entity);

    /**
     * Provides a link between web and persistence module.
     * Responsible for adding tag to the specified certificate.
     *
     * @param certificateId id of the certificate to which the tag should be added
     * @param tag           the tag to be added
     * @return updated {@link CertificateDTO}
     */
    CertificateDTO addTagToCertificate(long certificateId, TagDTO tag);

    /**
     * Provides a link between web and persistence module.
     * Responsible for deleting tag from the specified certificate.
     *
     * @param certificateId id of the certificate from which the tag should be added
     * @param tag           the tag to be deleted
     */
    void deleteTagFromCertificate(long certificateId, TagDTO tag);

    /**
     * Provides a link between web and persistence module.
     * Gets entity by specified params.
     *
     * @param paramMap map of params parsed from url
     * @return entities that meet the parameters specified in the specified map
     */
    PagedModel<CertificateDTO> findByCriteria(Map<String, String> paramMap, long page, long size);

}
