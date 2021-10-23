package com.epam.esm.assembler;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.controller.TagController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagRepresentationModelAssembler implements SimpleRepresentationModelAssembler<TagDTO> {
    @Override
    public void addLinks(EntityModel<TagDTO> resource) {
        long id = resource.getContent().getId();
        Link selfLink = linkTo(methodOn(TagController.class).read(id)).withSelfRel();
        Link deleteLink = linkTo(TagController.class).slash(id).withRel("delete");
        resource.add(selfLink, deleteLink);
    }

    public PagedModel<TagDTO> toPagedModel(PagedModel<TagDTO> page) {
        long pageNumber = page.getMetadata().getNumber();
        long size = page.getMetadata().getSize();
        long totalPages = page.getMetadata().getTotalPages();

        Link prevLink = linkTo(methodOn(TagController.class).findAll(pageNumber - 1, size)).withRel("prev page");
        Link nextLink = linkTo(methodOn(TagController.class).findAll(pageNumber + 1, size)).withRel("next page");
        Link selfLink = linkTo(methodOn(TagController.class).findAll(pageNumber, size)).withSelfRel();

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
    public void addLinks(CollectionModel<EntityModel<TagDTO>> resources) {

    }
}
