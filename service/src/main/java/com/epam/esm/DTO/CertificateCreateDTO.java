package com.epam.esm.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class CertificateCreateDTO extends RepresentationModel<CertificateDTO> {
    @Positive(message = "{message.positive.id}")
    private Long id;
    @NotBlank(message = "{message.not-blank.name}")
    @Size(min = 1, max = 45, message = "{message.size.name}")
    private String name;
    @NotBlank(message = "{message.not-blank.description}")
    @Size(min = 1, max = 200, message = "{message.size.description}")
    private String description;
    @Digits(integer = 13, fraction = 2, message = "{message.digits.price}")
    @PositiveOrZero(message = "{message.positive-or-zero.price}")
    @DecimalMax(value = "99999999999.99", message = "{message.max.price}")
    @NotNull(message = "{message.not-null.price}")
    private BigDecimal price;
    @Positive(message = "{message.positive.duration}")
    @NotNull(message = "{message.not-null.duration}")
    private Integer duration;
    private Set<@Valid TagDTO> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateDate;

    public void addTag(TagDTO tag) {
        tags.add(tag);
    }
}
