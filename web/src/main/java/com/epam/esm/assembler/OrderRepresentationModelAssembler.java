package com.epam.esm.assembler;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.controller.TagController;
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



    @Override
    public void addLinks(CollectionModel<EntityModel<OrderDTO>> resources) {

    }
}
