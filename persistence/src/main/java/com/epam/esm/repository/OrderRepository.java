package com.epam.esm.repository;

import com.epam.esm.Order;
import com.epam.esm.User;

import java.util.List;

public interface OrderRepository extends Repository<Order> {
    List<Order> readUserOrders(User user, long page, long size);

    Order create(long userId, long certificateId);

    long getCount(User user);
}
