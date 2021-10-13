package com.epam.esm.controller;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contains methods that process information in JSON received from this kind
 * of requests: '/tag/...'
 *
 * @author Mikhail Dakuchaev
 * @version 1.0
 * @see TagService
 * @see TagDTO
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    @Autowired
    private final TagService tagService;

    /**
     * Gets all tags that meet specified criteria
     *
     * @param params parameters that tag must satisfy
     * @return list of {@link TagDTO} in JSON  format
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> findAll(@RequestParam Map<String, String> params) {
        return tagService.findByCriteria(params);
    }

    /**
     * Gets tag by its id
     *
     * @param id id of the tag to be found
     * @return {@link TagDTO} with the specified ID if it exists in JSON format
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO read(@PathVariable("id") long id) {
        return tagService.read(id);
    }

    /**
     * Creates new tag with received information in JSON format
     *
     * @param tag {@link TagDTO} containing all necessary information
     * @return created {@link TagDTO} with up-to-date information in JSON format
     * @throws ValidationException if received information is not valid
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@RequestBody TagDTO tag) {
        return tagService.create(tag);
    }

    /**
     * Deletes tag with the specified id from the database
     *
     * @param id id of the tag to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        tagService.delete(id);
    }
}
