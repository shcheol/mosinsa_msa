package com.mosinsa.order.command.application;

import com.mosinsa.order.command.domain.OrderRepository;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.common.ex.OrderError;
import com.mosinsa.common.ex.OrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public Order cancelOrder(String orderId) {
		Order findOrder = orderRepository.findById(OrderId.of(orderId))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
		findOrder.cancelOrder();
		return findOrder;
	}
}
