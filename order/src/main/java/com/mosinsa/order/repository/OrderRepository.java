package com.mosinsa.order.repository;

import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerOrderByCreatedDateDesc(Customer customer);
}
