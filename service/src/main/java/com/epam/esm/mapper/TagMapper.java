package com.epam.esm.mapper;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TagMapper {

    @Autowired
    private final ModelMapper mapper;


    public TagDTO convertToDto(Tag tag) {
        return Objects.isNull(tag) ? null : mapper.map(tag, TagDTO.class);
    }

    public Tag convertToEntity(TagDTO tagDTO) {
        return Objects.isNull(tagDTO) ? null : mapper.map(tagDTO, Tag.class);
    }
}
