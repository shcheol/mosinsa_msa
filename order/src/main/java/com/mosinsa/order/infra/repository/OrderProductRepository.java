package com.mosinsa.order.infra.repository;

import com.mosinsa.order.db.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
