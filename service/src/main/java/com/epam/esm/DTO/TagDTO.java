package com.epam.esm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


/**
 * DTO required for interaction between web and persistence module and
 * performing intermediate actions (validation etc.)
 * Corresponds to {@link com.epam.esm.Tag}.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see com.epam.esm.Tag
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagDTO extends RepresentationModel<TagDTO> {
    @Positive
    private Long id;
    @NotBlank
    @Size(min = 1, max = 45)
    private String name;
}