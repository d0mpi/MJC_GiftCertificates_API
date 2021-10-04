package com.epam.esm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Certificate extends DatabaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<Tag> tags;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;

    public Certificate(long id, String name, String description, BigDecimal price, int duration, LocalDateTime create_date, LocalDateTime last_update_date) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
        this.tags = new LinkedList<>();
    }

    public void addTags(List<Tag> tag) {
        tags.addAll(tag);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id = '" + getId() +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                ", create_date=" + create_date +
                ", last_update_date=" + last_update_date +
                '}';
    }
}
