package com.mosinsa.order.infra.repository;

import com.mosinsa.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

    List<Order> findOrderByCustomerIdOrderByCreatedDateDesc(String customerId);
}
