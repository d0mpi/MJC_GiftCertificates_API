package com.epam.mjc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Certificate extends DatabaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private String create_date;
    private String last_update_date;
}
