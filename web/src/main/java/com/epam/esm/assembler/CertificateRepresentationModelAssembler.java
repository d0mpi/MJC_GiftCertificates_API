package com.epam.esm.assembler;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.controller.CertificateController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CertificateRepresentationModelAssembler implements SimpleRepresentationModelAssembler<CertificateDTO> {

    @Override
    public void addLinks(EntityModel<CertificateDTO> resource) {
        long id = resource.getContent().getId();
        Link selfLink = linkTo(methodOn(CertificateController.class).read(id)).withSelfRel();
        Link deleteLink = linkTo(CertificateController.class).slash(id).withRel("delete");
        resource.add(selfLink, deleteLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<CertificateDTO>> resources) {

    }


}
