package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "create_date")
    private LocalDateTime purchaseDate;

    @ManyToOne(targetEntity = Certificate.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;
    @ManyToOne(targetEntity = User.class, cascade = {
            CascadeType.REMOVE,
            CascadeType.MERGE
    })
    @JoinColumn(name = "user_id")
    private User user;
}
