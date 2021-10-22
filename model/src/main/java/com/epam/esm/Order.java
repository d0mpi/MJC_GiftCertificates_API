package com.epam.esm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "create_date")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime createDate;

    @ManyToOne(targetEntity = Certificate.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = {
            CascadeType.REMOVE,
            CascadeType.MERGE
    })
    @JoinColumn(name = "user_id")
    private User user;
}
