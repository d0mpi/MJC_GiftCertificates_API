package com.epam.esm.controller;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    @Autowired
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(tagService.findByCriteria(params), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") long id) {
        final TagDTO tag = tagService.read(id);
        return tag != null
                ? new ResponseEntity<>(tag, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody TagDTO tag) throws ValidationException {
        return new ResponseEntity<>(tagService.create(tag), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
