package com.epam.esm.service.impl;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.exception.EntityNotCreatedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderDTO create(OrderDTO entity) {
        return null;
    }

    @Transactional
    @Override
    public OrderDTO read(long id) {
        return orderMapper.convertToDto(
                orderRepository.read(id).orElseThrow(() -> (new EntityNotFoundException("message.not-found.order.id"))));
    }

    @Transactional
    @Override
    public void delete(long id) {
        orderRepository.read(id).orElseThrow(() -> (new EntityNotFoundException("message.not-found.order.id")));
        orderRepository.delete(orderMapper.convertToEntity(this.read(id)));
    }

    @Transactional
    @Override
    public PagedModel<OrderDTO> readAll(long page, long size) {
        List<OrderDTO> orderDTOList = orderRepository.readAll(page, size)
                .stream()
                .map(orderMapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, orderRepository.getCount());
        return PagedModel.of(orderDTOList, metadata);
    }

    @Transactional
    @Override
    public PagedModel<OrderDTO> getUserOrders(long userId, long page, long size) {
        User user = userRepository.read(userId).orElseThrow(() -> (new EntityNotFoundException("message.not-found.user.id")));
        List<OrderDTO> orderDTOList = orderRepository.readUserOrders(user, page, size)
                .stream().map(orderMapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, orderRepository.getCount(user));
        return PagedModel.of(orderDTOList, metadata);
    }

    @Transactional
    @Override
    public OrderDTO getUserOrder(long userId, long orderId) {
        Order order = orderRepository.read(orderId).orElseThrow(() -> (new EntityNotFoundException("message.not-found.order.id")));
        if (order.getUser().getId() != userId) {
            throw new EntityNotFoundException("message.invalid-user-order");
        }
        return orderMapper.convertToDto(order);
    }

    @Transactional
    @Override
    public OrderDTO create(long userId, long certificateId) {
        Certificate certificate = certificateRepository
                .read(certificateId).orElseThrow(() -> (new EntityNotFoundException("message.not-found.certificate.id")));
        User user = userRepository.read(userId).orElseThrow(() -> (new EntityNotFoundException("message.not-found.certificate.id")));
        Order order = new Order(null, certificate.getPrice(), LocalDateTime.now(), certificate, user);
        return orderMapper.convertToDto(orderRepository.create(
                order).orElseThrow(() -> (new EntityNotCreatedException("message.not-created.order"))));
    }
}
