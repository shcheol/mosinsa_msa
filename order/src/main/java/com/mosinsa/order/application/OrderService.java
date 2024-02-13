package com.mosinsa.order.application;

import com.mosinsa.order.dto.CreateOrderDto;
import com.mosinsa.order.dto.OrderDetailDto;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.ui.request.CancelOrderRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;


public interface OrderService {

	OrderDetailDto order(CreateOrderDto createOrderDto);

	void cancelOrder(Map<String, Collection<String>> headers, CancelOrderRequest request);

	Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable);

	OrderDetailDto getOrderDetails(String orderId);

}
