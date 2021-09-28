package com.epam.mjc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Certificate extends DatabaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;

    public Certificate(int id, String name, String description, BigDecimal price, int duration, LocalDateTime create_date, LocalDateTime last_update_date) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
    }
}
