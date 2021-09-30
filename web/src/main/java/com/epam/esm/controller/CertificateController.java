package com.epam.esm.controller;

import com.epam.esm.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public ResponseEntity<?> findAllByCriteria(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(certificateService.findByCriteria(params), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") long id) {
        final Certificate certificate = certificateService.read(id);
        return certificate != null
                ? new ResponseEntity<>(certificate, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody Certificate certificate) {
        if (certificate.getName() != null
                && certificate.getDuration() != null
                && certificate.getDescription() != null
                && certificate.getPrice() != null) {
            return new ResponseEntity<>(certificateService.create(certificate), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("wrong entity", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping(value = "/{id}",
            consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Certificate patch) {
        Certificate certificate = certificateService.read(id);
        if (patch.getName() != null)
            certificate.setName(patch.getName());
        if (patch.getDuration() != null)
            certificate.setDuration(patch.getDuration());
        if (patch.getDescription() != null)
            certificate.setDescription(patch.getDescription());
        if (patch.getPrice() != null)
            certificate.setPrice(patch.getPrice());
        return new ResponseEntity<>(certificateService.update(certificate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable("id") long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
