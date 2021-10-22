package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.Order;
import com.epam.esm.User;

import java.util.List;

public interface OrderService extends EntityService<OrderDTO> {
    List<OrderDTO> getUserOrders(long userId, int page, int limit);

    OrderDTO getUserOrder(long userId, long orderId);

    OrderDTO createOrder(long userId, long certificateId);
}
