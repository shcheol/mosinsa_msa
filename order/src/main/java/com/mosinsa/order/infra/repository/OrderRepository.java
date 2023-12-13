package com.mosinsa.order.infra.repository;

import com.mosinsa.order.domain.Order;
import com.mosinsa.order.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderId>, CustomOrderRepository {

}
