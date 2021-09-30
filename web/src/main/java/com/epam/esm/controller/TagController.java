package com.epam.esm.controller;

import com.epam.esm.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(tagService.findByCriteria(params), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") long id){
        final Tag tag = tagService.read(id);
        return tag != null
                ? new ResponseEntity<>(tag, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody Tag tag){
        if(tag.getName() != null) {
            return new ResponseEntity<>(tagService.create(tag), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("wrong entity", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
