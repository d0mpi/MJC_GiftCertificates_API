package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.UserDTO;

import java.util.List;

public interface UserService extends EntityService<UserDTO> {
    TagDTO getMostWidelyUsedTag(long userId);
}
