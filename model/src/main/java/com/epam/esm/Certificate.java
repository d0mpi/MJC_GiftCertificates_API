package com.epam.esm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                "id =" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                ", create_date=" + create_date +
                ", last_update_date=" + last_update_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Certificate that = (Certificate) o;
        this.tags = this.tags.stream().sorted().collect(Collectors.toList());
        ((Certificate) o).setTags(((Certificate) o).getTags().stream().sorted().collect(Collectors.toList()));
        return name.equals(that.name) && description.equals(that.description) && price.equals(that.price) && duration.equals(that.duration) && Objects.equals(tags, that.tags) && Objects.equals(create_date, that.create_date) && Objects.equals(last_update_date, that.last_update_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration, tags, create_date, last_update_date);
    }
}
