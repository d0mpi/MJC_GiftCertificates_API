package com.epam.esm.service.impl;

import com.epam.esm.Certificate;
import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

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

    @Override
    public OrderDTO create(OrderDTO entity) {
        return null;
    }

    @Override
    public OrderDTO read(long id) {
        return orderMapper.convertToDto(
                orderRepository.read(id).orElseThrow(() -> (new EntityNotFoundException("order", 40404))));
    }

    @Override
    public void delete(long id) {
        orderRepository.delete(id);
    }

    @Override
    public PagedModel<OrderDTO> readAll(long page, long size) {
        List<OrderDTO> orderDTOList = orderRepository.readAll(page, size)
                .stream()
                .map(orderMapper::convertToDto)
                .collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page, orderRepository.getCount());
        return PagedModel.of(orderDTOList, metadata);
    }

    @Override
    public List<OrderDTO> getUserOrders(long userId, long page, long size) {
        return orderRepository.readUserOrders(userId, page, size)
                .stream()
                .map(orderMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getUserOrder(long userId, long orderId) {
        Order order = orderRepository.read(orderId).orElseThrow(() -> (new EntityNotFoundException("order", 40404)));
        if (order.getUser().getId() != userId) {
            throw new EntityNotFoundException("order", 40404);
        }
        return orderMapper.convertToDto(order);
    }

    @Override
    public OrderDTO create(long userId, long certificateId) {
        Certificate certificate = certificateRepository
                .read(certificateId).orElseThrow(() -> (new EntityNotFoundException("certificate", 40401)));
        User user = userRepository.read(userId).orElseThrow(() -> (new EntityNotFoundException("user", 40403)));
        Order order = new Order(null, certificate.getPrice(), LocalDateTime.now(), certificate, user);
        return orderMapper.convertToDto(orderRepository.create(
                new Order(null,
                        certificate.getPrice(),
                        LocalDateTime.now(),
                        certificate,
                        user)).orElseThrow(() -> (new EntityNotFoundException("certificate", 40401))));
    }
}
