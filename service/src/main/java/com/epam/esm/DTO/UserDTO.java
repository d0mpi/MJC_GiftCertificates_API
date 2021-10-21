package com.epam.esm.DTO;

import com.epam.esm.Order;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDTO extends EntityDTO {
    private String userName;
    private String email;
    private String password;
    private Set<Order> orderSet;
    private LocalDateTime registrationDate;
}
