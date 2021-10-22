package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {
    @Override
    public List<Order> findOrdersPageByUser(User user, int page, int limit) {
        return null;
    }

    @Override
    public List<Order> readUserOrders(long userId, int page, int limit) {
        return null;
    }

    @Override
    public Optional<Order> create(Order entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> read(long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> readAll(int page, int limit) {
        return null;
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }
}
