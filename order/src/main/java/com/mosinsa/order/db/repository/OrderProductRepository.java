package com.mosinsa.order.db.repository;

import com.mosinsa.order.db.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
