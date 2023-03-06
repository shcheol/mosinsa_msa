package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
