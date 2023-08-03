package com.mosinsa.order.service;


import com.mosinsa.order.db.dto.OrderDto;
import com.mosinsa.order.db.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface OrderService {

    OrderDto order(Long customerId, Map<String, Integer> productMap);
    void cancelOrder(Long customerId, Long orderId);
	List<OrderDto> getOrderCustomer(Long customerId);
    Page<OrderDto> getOrders(Pageable pageable);

    OrderDto findOrderById(Long orderId);

	void changeOrderStatus(Long orderId, OrderStatus status);

    int getTotalPrice(Long orderId);
}
