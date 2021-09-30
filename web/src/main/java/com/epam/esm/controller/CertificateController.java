package com.epam.esm.controller;

import com.epam.esm.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.impl.JdbcCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private final JdbcCertificateRepository certificateRepository;

    @Autowired
    public CertificateController(JdbcCertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        Collection<Certificate> collection = certificateRepository.findAll();
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }
}
