package com.epam.esm.assembler;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserRepresentationModelAssembler implements SimpleRepresentationModelAssembler<UserDTO> {
    @Override
    public void addLinks(EntityModel<UserDTO> resource) {
        long id = resource.getContent().getId();
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        Link deleteLink = linkTo(UserController.class).slash(id).withRel("delete");
        resource.add(selfLink, deleteLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<UserDTO>> resources) {

    }
}
