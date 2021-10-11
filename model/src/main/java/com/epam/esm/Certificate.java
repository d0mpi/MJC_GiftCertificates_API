package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains information about certificate, provides
 * methods and constructors to interact with this
 * information
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see DatabaseEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Certificate extends DatabaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Set<Tag> tags;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;


    /**
     * All args certificate constructor
     *
     * @param id               id of the certificate
     * @param name             name of the certificate
     * @param description      description of the certificate
     * @param price            price of the certificate
     * @param duration         duration of the certificate
     * @param create_date      date and time of the certificate creation
     * @param last_update_date date and time of the certificate's last update
     */
    public Certificate(long id, String name, String description, BigDecimal price, int duration, LocalDateTime create_date, LocalDateTime last_update_date) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
        this.tags = new HashSet<>();
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
}
