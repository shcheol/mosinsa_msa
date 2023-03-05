package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
