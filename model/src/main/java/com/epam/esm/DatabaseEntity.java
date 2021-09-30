package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DatabaseEntity {
    private long id;

    public DatabaseEntity() {
    }

    public DatabaseEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
