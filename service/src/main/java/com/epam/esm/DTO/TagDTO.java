package com.epam.esm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * DTO required for interaction between web and persistence module and
 * performing intermediate actions (validation etc.)
 * Corresponds to {@link com.epam.esm.Tag}.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see com.epam.esm.Tag
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends EntityDTO {

    private String name;

    public TagDTO(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "id" + super.getId() +
                "name='" + name + '\'' +
                '}';
    }
}