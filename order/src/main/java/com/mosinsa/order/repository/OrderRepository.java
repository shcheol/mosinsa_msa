package com.mosinsa.order.repository;

import com.mosinsa.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByCustomerIdOrderByCreatedDateDesc(Long customerId);
}
