package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tag extends DatabaseEntity {
    private String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
