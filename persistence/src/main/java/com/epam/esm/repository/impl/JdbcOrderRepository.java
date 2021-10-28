package com.epam.esm.repository.impl;

import com.epam.esm.Certificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> readUserOrders(User user, long page, long size) {
        return entityManager.createQuery("select o from Order o where o.user = :user", Order.class)
                .setParameter("user", user)
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();
    }

    @Override
    public Order create(long userId, long certificateId) {
        return null;
    }

    @Override
    public boolean isCertificateAssociatedWithOrder(Certificate certificate) {
        return entityManager.createQuery("select o from Order o where o.certificate = :certificate", Order.class)
                .setParameter("certificate", certificate)
                .getResultList().size() != 0;
    }

    @Override
    public boolean isUserAssociatedWithOrder(User user) {
        return entityManager.createQuery("select o from Order o where o.user = :user", Order.class)
                .setParameter("user", user)
                .getResultList().size() != 0;
    }

    @Override
    public long getCount(User user) {
        return entityManager.createQuery("select count(o) from Order o where o.user = :user", Long.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public Optional<Order> create(Order order) {
        entityManager.persist(order);
        return Optional.of(order);
    }

    @Override
    public Optional<Order> read(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> readAll(long page, long size) {
        return entityManager.createQuery("select o from Order o")
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        return Optional.empty();
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public long getCount() {
        return (long) entityManager.createQuery("SELECT COUNT(o) FROM Order o").getSingleResult();
    }
}
