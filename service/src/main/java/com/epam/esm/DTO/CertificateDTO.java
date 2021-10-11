package com.epam.esm.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO required for interaction between web and persistence module and
 * performing intermediate actions (validation etc.)
 * Corresponds to {@link com.epam.esm.Certificate}.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see com.epam.esm.Certificate
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO extends EntityDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Set<TagDTO> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime create_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime last_update_date;

    public CertificateDTO(Long id, String name,
                          String description,
                          BigDecimal price,
                          Integer duration,
                          Set<TagDTO> tags,
                          LocalDateTime create_date,
                          LocalDateTime last_update_date) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
    }

    public void addTag(TagDTO tag) {
        tags.add(tag);
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "id=" + this.getId() +
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
