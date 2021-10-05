package com.epam.esm;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tag extends DatabaseEntity implements Comparable<Tag>, Serializable {
    private String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Tag o) {
        return (int) (o.getId() - this.getId());
    }
}
