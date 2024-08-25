package com.mosinsa.order.command.domain;

import com.mosinsa.order.infra.jpa.CustomOrderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends Repository<Order, OrderId>, CustomOrderRepository {

    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
    @Query("select o from Order o inner join fetch o.orderProducts where o.id = :orderId")
    Optional<Order> findOrderDetailsById(@Param("orderId") OrderId orderId);
}
