package com.epam.esm.service.impl;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;

    @Override
    public UserDTO create(UserDTO entity) {
        return null;
    }

    @Override
    public UserDTO read(long id) {
        return mapper.convertToDto(repo.read(id).orElseThrow(() -> (new EntityNotFoundException("user", 40403))));
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public List<UserDTO> readAll(int page, int login) {
        return null;
    }

    @Override
    public TagDTO getMostWidelyUsedTag(long userId) {
        return null;
    }
}
