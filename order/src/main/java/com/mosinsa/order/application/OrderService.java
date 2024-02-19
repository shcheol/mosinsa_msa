package com.mosinsa.order.application;

import com.mosinsa.order.dto.CreateOrderDto;
import com.mosinsa.order.dto.OrderDetailDto;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

	OrderDetailDto order(CreateOrderDto createOrderDto);

	OrderDetailDto cancelOrder(String orderId);

	Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable);

	OrderDetailDto getOrderDetails(String orderId);

}
