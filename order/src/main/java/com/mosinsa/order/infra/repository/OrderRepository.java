package com.mosinsa.order.infra.repository;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderId>, CustomOrderRepository {

}
