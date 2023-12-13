package com.mosinsa.order.application;

import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.ui.request.OrderCreateRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface OrderService {

	OrderDto order(OrderCreateRequest productMap, Map<String, Collection<String>> authMap);

	void cancelOrder(String customerId, String orderId, Map<String, Collection<String>> authMap);

	List<OrderDto> getOrderCustomer(String customerId);

	Page<OrderDto> findOrdersByCondition(SearchCondition condition, Pageable pageable);

	OrderDto findOrderById(String orderId);

}
