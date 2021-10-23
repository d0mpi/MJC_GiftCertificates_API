package com.epam.esm.service.impl;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDTO create(UserDTO entity) {
        return null;
    }

    @Override
    public UserDTO read(long id) {
        return mapper.convertToDto(userRepository.read(id).orElseThrow(() -> (new EntityNotFoundException("user", 40403))));
    }

    @Override
    public void delete(long id) {
        userRepository.delete(mapper.convertToEntity(this.read(id)));
    }

    @Override
    public PagedModel<UserDTO> readAll(long page, long login) {
        return null;
    }

}
