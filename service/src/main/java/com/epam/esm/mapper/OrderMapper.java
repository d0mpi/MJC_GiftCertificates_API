package com.epam.esm.mapper;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    @Autowired
    protected final ModelMapper mapper;

    public OrderDTO convertToDto(Order order) {
        return Objects.isNull(order) ? null : mapper.map(order, OrderDTO.class);
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        return Objects.isNull(orderDTO) ? null : mapper.map(orderDTO, Order.class);
    }
}
