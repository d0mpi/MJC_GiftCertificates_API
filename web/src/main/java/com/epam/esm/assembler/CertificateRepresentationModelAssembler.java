package com.epam.esm.assembler;

import com.epam.esm.DTO.CertificateDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;

public class CertificateRepresentationModelAssembler implements SimpleRepresentationModelAssembler<CertificateDTO> {

    @Override
    public void addLinks(EntityModel<CertificateDTO> resource) {

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<CertificateDTO>> resources) {

    }


}
