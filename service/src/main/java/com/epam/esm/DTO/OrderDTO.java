package com.epam.esm.DTO;

import com.epam.esm.Certificate;
import com.epam.esm.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends EntityDTO {
    private Certificate certificate;
    private User user;
    private BigDecimal cost;
    private LocalDateTime createDate;
}
