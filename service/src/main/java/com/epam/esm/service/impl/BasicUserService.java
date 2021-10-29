package com.epam.esm.service.impl;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.User;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotCreatedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.OrderIsAssociatedWithDatabaseEntity;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO create(UserDTO user) {
        user.setRegistrationDate(LocalDateTime.now());
        if (userRepository.exists(userMapper.convertToEntity(user))) {
            throw new EntityAlreadyExistsException("message.exists.user");
        }
        return userMapper.convertToDto(
                userRepository.create(userMapper.convertToEntity(user))
                        .orElseThrow(() -> (new EntityNotCreatedException("message.not-created.user"))));
    }

    @Override
    @Transactional
    public UserDTO read(long id) {
        return userMapper.convertToDto(userRepository.read(id).orElseThrow(() -> (new EntityNotFoundException("message.not-found.user"))));
    }

    @Override
    @Transactional
    public void delete(long id) {
        User user = userRepository.read(id).orElseThrow(() -> (new EntityNotFoundException("message.not-found.user")));
        if (orderRepository.isUserAssociatedWithOrder(user)) {
            throw new OrderIsAssociatedWithDatabaseEntity("message.is-associated.user");
        }
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public PagedModel<UserDTO> readAll(long page, long size) {
        List<UserDTO> userDTOS = userRepository.readAll(page, size)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, userRepository.getCount());
        return PagedModel.of(userDTOS, metadata);
    }

}
