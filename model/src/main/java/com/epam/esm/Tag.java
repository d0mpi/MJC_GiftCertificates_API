package com.epam.esm;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Contains information about tag, provides
 * methods and constructors to interact with this
 * information
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "tag")
public class Tag implements Comparable<Tag>, Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    /**
     * All args tag constructor
     *
     * @param id   tag id
     * @param name tag name
     */
    @Builder
    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
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
