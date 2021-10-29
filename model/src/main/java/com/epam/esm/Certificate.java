package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains information about certificate, provides
 * methods and constructors to interact with this
 * information
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "certificate")
public class Certificate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "duration")
    private Integer duration;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "certificate_tag",
            joinColumns = @JoinColumn(name = "certificate_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
    private Set<Tag> tags;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;


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
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = create_date;
        this.lastUpdateDate = last_update_date;
        this.tags = new HashSet<>();
    }

    public void addTags(List<Tag> tag) {
        tags.addAll(tag);
    }

}
