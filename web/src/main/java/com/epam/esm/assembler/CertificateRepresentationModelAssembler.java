package com.epam.esm.assembler;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.controller.CertificateController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateRepresentationModelAssembler implements SimpleRepresentationModelAssembler<CertificateDTO> {

    @Override
    public void addLinks(EntityModel<CertificateDTO> resource) {
        long id = resource.getContent().getId();
        Link selfLink = linkTo(methodOn(CertificateController.class).read(id)).withSelfRel();
        Link deleteLink = linkTo(CertificateController.class).slash(id).withRel("delete");
        resource.add(selfLink, deleteLink);
    }

    public PagedModel<CertificateDTO> toPagedModel(PagedModel<CertificateDTO> page, Map<String, String> params) {
        long pageNumber = page.getMetadata().getNumber();
        long size = page.getMetadata().getSize();
        long totalPages = page.getMetadata().getTotalPages();

        Link prevLink = linkTo(methodOn(CertificateController.class)
                .findAllByCriteria(params, pageNumber - 1, size)).withRel("prev page");
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .findAllByCriteria(params, pageNumber + 1, size)).withRel("next page");
        Link selfLink = linkTo(methodOn(CertificateController.class)
                .findAllByCriteria(params, pageNumber, size)).withSelfRel();
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
    public void addLinks(CollectionModel<EntityModel<CertificateDTO>> resources) {
    }
}
