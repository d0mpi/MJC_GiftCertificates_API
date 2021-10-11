package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Contains daatbase entity' id, provides
 * methods and constructors to interact with this
 * information
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseEntity {
    private long id;
}
