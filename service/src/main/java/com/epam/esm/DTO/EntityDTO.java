package com.epam.esm.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityDTO {

    private Long id;

    @Override
    public String toString() {
        return "AbstractDto{" +
                "id=" + id +
                '}';
    }
}
