package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Contains information about tag, provides
 * methods and constructors to interact with this
 * information
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see DatabaseEntity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tag extends DatabaseEntity implements Comparable<Tag>, Serializable {
    private String name;

    /**
     * All args tag constructor
     *
     * @param id   tag id
     * @param name tag name
     */
    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Compares two {@link Tag} instances by id
     *
     * @param o {@link Tag} to be compared
     * @return 0 - if objects are equals
     * positive number - if id of the {@link Tag} o is more than id of the object whoose method was called
     * negative number - if id of the {@link Tag} o is less than id of the object whoose method was called
     */
    @Override
    public int compareTo(Tag o) {
        return (int) (o.getId() - this.getId());
    }
}
