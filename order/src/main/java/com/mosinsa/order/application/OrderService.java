package com.mosinsa.order.application;

import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

	OrderDto order(CreateOrderRequest request);

	void cancelOrder(String customerId, String orderId);

	Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable);

	OrderDto getOrderDetails(String orderId);

}
