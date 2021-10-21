package com.epam.esm.service.impl;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasicOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO create(OrderDTO entity) {
        return null;
    }

    @Override
    public OrderDTO read(long id) {
        return null;
    }

    @Override
    public List<OrderDTO> findByCriteria(Map<String, String> paramMap) {
        return null;
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public List<OrderDTO> findOrdersPageByUser(UserDTO userDTO, int page, int limit) {
        return null;
    }
}
