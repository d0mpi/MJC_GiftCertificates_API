package com.epam.esm.assembler;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRepresentationModelAssembler implements SimpleRepresentationModelAssembler<UserDTO> {
    @Override
    public void addLinks(EntityModel<UserDTO> resource) {
        long id = resource.getContent().getId();
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        resource.add(selfLink);
    }

    public PagedModel<UserDTO> toPagedModel(PagedModel<UserDTO> page) {
        long pageNumber = page.getMetadata().getNumber();
        long size = page.getMetadata().getSize();
        long totalPages = page.getMetadata().getTotalPages();

        Link prevLink = linkTo(methodOn(UserController.class).findAll(pageNumber - 1, size)).withRel("prev page");
        Link nextLink = linkTo(methodOn(UserController.class).findAll(pageNumber + 1, size)).withRel("next page");
        Link selfLink = linkTo(methodOn(UserController.class).findAll(pageNumber, size)).withSelfRel();
        page.add(selfLink);
        if (pageNumber > 1) {
            page.add(prevLink);
        }
        if (pageNumber < totalPages) {
            page.add(nextLink);
        }
        page.getContent().forEach(this::toModel);
        return page;
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<UserDTO>> resources) {

    }
}
