package com.epam.esm.service.impl;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    @Override
    public UserDTO create(UserDTO entity) {
        return null;
    }

    @Override
    public UserDTO read(long id) {
        return null;
    }

    @Override
    public List<UserDTO> findByCriteria(Map<String, String> paramMap) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
