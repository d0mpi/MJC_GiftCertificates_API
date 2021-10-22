package com.epam.esm.repository;

import com.epam.esm.Order;
import com.epam.esm.User;

import java.util.List;

public interface OrderRepository extends Repository<Order> {
    List<Order> findOrdersPageByUser(User user, int page, int limit);

    List<Order> readUserOrders(long userId, int page, int limit);
}
