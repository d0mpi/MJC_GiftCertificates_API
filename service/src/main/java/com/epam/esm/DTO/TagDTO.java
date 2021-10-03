package com.epam.esm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends EntityDTO {

    private String name;

    @Override
    public String toString() {
        return "TagDTO{" +
                "id" + super.getId() +
                "name='" + name + '\'' +
                '}';
    }
}