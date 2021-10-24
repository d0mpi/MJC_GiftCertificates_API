package com.epam.esm.controller;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.assembler.CertificateRepresentationModelAssembler;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Contains methods that process information in JSON received from this kind
 * of requests: '/certificate/...'
 *
 * @author Mikhail Dakuchaev
 * @version 1.0
 * @see CertificateService
 * @see CertificateDTO
 */
@RestController
@Slf4j
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateRepresentationModelAssembler certificateAssembler;

    /**
     * Gets all certificates that meet specified criteria
     *
     * @param params parameters that certificates must satisfy
     * @return list of {@link CertificateDTO} in JSON format
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<CertificateDTO> findAllByCriteria(@RequestParam
                                                                Map<String, String> params,
                                                        @RequestParam(value = "page", required = false, defaultValue = "1")
                                                                long page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "10")
                                                                long size) {
        PagedModel<CertificateDTO> certificateDTOList = certificateService.findByCriteria(params, page, size);
        return certificateAssembler.toPagedModel(certificateDTOList, params);
    }

    /**
     * Gets certificate by its id
     *
     * @param id id of the certificate to be found
     * @return {@link CertificateDTO} with the specified ID if it exists in JSON format
     */
    @GetMapping("/{id}")
    @ResponseBody
    public EntityModel<CertificateDTO> read(@PathVariable("id") long id) {
        return certificateAssembler.toModel(certificateService.read(id));
    }

    /**
     * Creates new certificate with received information in JSON format
     *
     * @param certificate {@link CertificateDTO} containing all necessary information
     * @return created {@link CertificateDTO} with up-to-date information in JSON format
     * @throws ValidationException if received information is not valid
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<CertificateDTO> create(@RequestBody @Valid CertificateDTO certificate) {
        return certificateAssembler.toModel(certificateService.create(certificate));
    }

    /**
     * Updates part of the certificate information
     *
     * @param id    id of the certificate to be updated
     * @param patch {@link CertificateDTO} with information to be updated
     * @return {@link CertificateDTO} with up-to-date information in JSON format
     * @throws ValidationException if certificate with the specified id does not exist
     */
    @PatchMapping(value = "/{id}",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<CertificateDTO> update(@PathVariable("id") long id, @RequestBody @Valid CertificateDTO patch) {
        patch.setId(id);
        return certificateAssembler.toModel(certificateService.update(patch));
    }

    /**
     * Deletes certificate with the specified id from the database
     *
     * @param id id of the certificate to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable("id") long id) {
        certificateService.delete(id);
    }

    /**
     * Add new or existing tag to the certificate
     *
     * @param certificateId id of the certificate to be updated
     * @param tag           tag to be added
     * @return {@link CertificateDTO} with up-to-date information in JSON format
     */
    @PostMapping(value = "/{certificateId}/tag")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<CertificateDTO> addTagToCertificate(@PathVariable("certificateId") long certificateId,
                                                           @RequestBody TagDTO tag) {
        return certificateAssembler.toModel(certificateService.addTagToCertificate(certificateId, tag));
    }

    /**
     * Delete tag from the certificate
     *
     * @param certificateId id of the certificate to be updated
     * @param tag           tag to be deleted
     */
    @DeleteMapping(value = "/{certificateId}/tag")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagFromCertificate(@PathVariable("certificateId") long certificateId,
                                         @RequestBody TagDTO tag) {
        certificateService.deleteTagFromCertificate(certificateId, tag);
    }
}
