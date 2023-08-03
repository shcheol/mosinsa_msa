package com.mosinsa.order.db.repository;

import com.mosinsa.order.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByCustomerIdOrderByCreatedDateDesc(Long customerId);
}
