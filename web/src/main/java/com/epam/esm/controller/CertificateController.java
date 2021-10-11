package com.epam.esm.controller;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private final CertificateService certificateService;

    /**
     * Gets all certificates that meet specified criteria
     *
     * @param params parameters that certificates must satisfy
     * @return list of {@link CertificateDTO} in JSON format
     */
    @GetMapping
    public ResponseEntity<?> findAllByCriteria(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(certificateService.findByCriteria(params), HttpStatus.OK);
    }

    /**
     * Gets certificate by its id
     *
     * @param id id of the certificate to be found
     * @return {@link CertificateDTO} with the specified ID if it exists in JSON format
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CertificateDTO read(@PathVariable("id") long id) {
        return certificateService.read(id);
    }

    /**
     * Creates new certificate with received information in JSON format
     *
     * @param certificate {@link CertificateDTO} containing all necessary information
     * @return created {@link CertificateDTO} with up-to-date information in JSON format
     * @throws ValidationException if received information is not valid
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody CertificateDTO certificate) {
        return new ResponseEntity<>(certificateService.create(certificate), HttpStatus.CREATED);
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
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody CertificateDTO patch) {
        patch.setId(id);
        return new ResponseEntity<>(certificateService.update(patch), HttpStatus.CREATED);
    }

    /**
     * Deletes certificate with the specified id from the database
     *
     * @param id id of the certificate to be deleted
     * @return {@link HttpStatus)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable("id") long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add new or existing tag to the certificate
     *
     * @param certificateId id of the certificate to be updated
     * @param tag           tag to be added
     * @return {@link CertificateDTO} with up-to-date information in JSON format
     */
    @PostMapping(value = "/{certificateId}/tag")
    public ResponseEntity<?> addTagToCertificate(@PathVariable("certificateId") long certificateId,
                                                 @RequestBody TagDTO tag) {
        return new ResponseEntity<>(certificateService.addTagToCertificate(certificateId, tag), HttpStatus.OK);
    }

    /**
     * Delete tag from the certificate
     *
     * @param certificateId id of the certificate to be updated
     * @param tag           tag to be deleted
     * @return {@link HttpStatus)
     */
    @DeleteMapping(value = "/{certificateId}/tag")
    public ResponseEntity<?> deleteTagFromCertificate(@PathVariable("certificateId") long certificateId,
                                                      @RequestBody TagDTO tag) {
        certificateService.deleteTagFromCertificate(certificateId, tag);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
