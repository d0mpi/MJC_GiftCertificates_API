package com.epam.esm.mapper;

import com.epam.esm.DTO.CertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Contains method required for conversion object from
 * {@link TagDTO} to {@link Tag} and vice-verse
 * with the help of {@link ModelMapper}
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see CertificateDTO
 * @see java.security.cert.Certificate
 */
@Component
@RequiredArgsConstructor
public class TagMapper {

    @Autowired
    private final ModelMapper mapper;

    /**
     * Converts {@link Tag} to {@link TagDTO} with the help of {@link ModelMapper}
     *
     * @param tag {@link Tag} to be converted
     * @return {@link TagDTO} converted from the specified {@link Tag}
     */
    public TagDTO convertToDto(Tag tag) {
        return Objects.isNull(tag) ? null : mapper.map(tag, TagDTO.class);
    }

    /**
     * Converts {@link TagDTO} to {@link Tag} with the help of {@link ModelMapper}
     *
     * @param tagDTO {@link Tag} to be converted
     * @return {@link Tag} converted from the specified {@link TagDTO}
     */
    public Tag convertToEntity(TagDTO tagDTO) {
        return Objects.isNull(tagDTO) ? null : mapper.map(tagDTO, Tag.class);
    }
}
