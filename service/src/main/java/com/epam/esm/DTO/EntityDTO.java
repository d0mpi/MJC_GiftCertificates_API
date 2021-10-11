package com.epam.esm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO required for interaction between web and persistence module and
 * performing intermediate actions (validation etc.)
 * Corresponds to {@link com.epam.esm.DatabaseEntity}.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see com.epam.esm.DatabaseEntity
 */
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
