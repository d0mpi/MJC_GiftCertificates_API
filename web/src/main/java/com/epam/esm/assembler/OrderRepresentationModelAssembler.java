package com.epam.esm.assembler;

import com.epam.esm.DTO.OrderDTO;
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
public class OrderRepresentationModelAssembler implements SimpleRepresentationModelAssembler<OrderDTO> {
    @Override
    public void addLinks(EntityModel<OrderDTO> resource) {
        long userId = resource.getContent().getUser().getId();
        long certificateId = resource.getContent().getCertificate().getId();
        long orderId = resource.getContent().getId();
        Link selfLink = linkTo(methodOn(UserController.class).getUserOrder(userId, orderId)).withSelfRel();
        resource.add(selfLink);
    }

    public PagedModel<OrderDTO> toPagedModel(PagedModel<OrderDTO> page, long userId) {
        long pageNumber = page.getMetadata().getNumber();
        long size = page.getMetadata().getSize();
        long totalPages = page.getMetadata().getTotalPages();

        Link prevLink = linkTo(methodOn(UserController.class).getUserOrders(userId, pageNumber - 1, size)).withRel("prev page");
        Link nextLink = linkTo(methodOn(UserController.class).getUserOrders(userId, pageNumber + 1, size)).withRel("next page");
        Link selfLink = linkTo(methodOn(UserController.class).getUserOrders(userId, pageNumber, size)).withSelfRel();

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

    public PagedModel<OrderDTO> toPagedModel(PagedModel<OrderDTO> page) {
        long pageNumber = page.getMetadata().getNumber();
        long size = page.getMetadata().getSize();
        long totalPages = page.getMetadata().getTotalPages();

        Link prevLink = linkTo(methodOn(UserController.class).findAllOrders(pageNumber - 1, size)).withRel("prev page");
        Link nextLink = linkTo(methodOn(UserController.class).findAllOrders(pageNumber + 1, size)).withRel("next page");
        Link selfLink = linkTo(methodOn(UserController.class).findAllOrders(pageNumber, size)).withSelfRel();

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
    public void addLinks(CollectionModel<EntityModel<OrderDTO>> resources) {

    }
}
