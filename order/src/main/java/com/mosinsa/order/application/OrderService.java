package com.mosinsa.order.application;

import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.ui.request.CancelOrderRequest;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;


public interface OrderService {

	OrderDto order(Map<String, Collection<String>> headers, CreateOrderRequest request);

	void cancelOrder(Map<String, Collection<String>> headers, CancelOrderRequest request);

	Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable);

	OrderDto getOrderDetails(String orderId);

}
