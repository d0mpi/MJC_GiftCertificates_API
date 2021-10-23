package com.epam.esm.controller;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.assembler.TagRepresentationModelAssembler;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    private final TagService tagService;
    private final TagRepresentationModelAssembler tagAssembler;

    /**
     * Gets all tags that meet specified criteria
     *
     * @return list of {@link TagDTO} in JSON  format
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<TagDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "1")
                                              long page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10")
                                              long size) {
        PagedModel<TagDTO> tagPage = tagService.readAll(page, size);
        return tagAssembler.toPagedModel(tagPage);
    }

    /**
     * Gets tag by its id
     *
     * @param id id of the tag to be found
     * @return {@link TagDTO} with the specified ID if it exists in JSON format
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDTO> read(@PathVariable("id") long id) {
        return tagAssembler.toModel(tagService.read(id));
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
    public TagDTO create(@RequestBody @Valid TagDTO tag) {
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
