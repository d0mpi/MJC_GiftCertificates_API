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

@RestController
@Slf4j
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class CertificateController {

    @Autowired
    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<?> findAllByCriteria(@RequestParam Map<String, String> params) {
        for (var param : params.entrySet()) {
            System.out.println(param.getKey() + " : " + param.getValue());
        }
        return new ResponseEntity<>(certificateService.findByCriteria(params), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CertificateDTO read(@PathVariable("id") long id) {
        return certificateService.read(id);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody CertificateDTO certificate) throws ValidationException {
        return new ResponseEntity<>(certificateService.create(certificate), HttpStatus.CREATED);
    }

    @PatchMapping (value = "/{id}",
            consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody CertificateDTO patch) throws ValidationException {
        CertificateDTO certificate = certificateService.read(id);
        if (patch.getName() != null)
            certificate.setName(patch.getName());
        if (patch.getDuration() != null)
            certificate.setDuration(patch.getDuration());
        if (patch.getDescription() != null)
            certificate.setDescription(patch.getDescription());
        if (patch.getPrice() != null)
            certificate.setPrice(patch.getPrice());
        if (patch.getTags() != null)
            certificate.setTags(patch.getTags());
        return new ResponseEntity<>(certificateService.update(certificate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable("id") long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{certificateId}/tag")
    public ResponseEntity<?> addTagToCertificate(@PathVariable("certificateId") long certificateId,
                                                 @RequestBody TagDTO tag){
        return new ResponseEntity<>(certificateService.addTagToCertificate(certificateId, tag), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{certificateId}/tag")
    public ResponseEntity<?> deleteTagFromCertificate(@PathVariable("certificateId") long certificateId,
                                                 @RequestBody TagDTO tag){
        certificateService.deleteTagFromCertificate(certificateId, tag);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
