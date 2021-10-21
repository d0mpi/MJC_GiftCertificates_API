package com.epam.esm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "order")
public class Order extends DatabaseEntity {
    @ManyToOne
    @JoinColumn(name = "id")
    private Certificate certificate;
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime createDate;
}
