package com.epam.esm.mapper;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {
    protected final ModelMapper mapper;

    public UserDTO convertToDto(User user) {
        return Objects.isNull(user) ? null : mapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return Objects.isNull(userDTO) ? null : mapper.map(userDTO, User.class);
    }
}
