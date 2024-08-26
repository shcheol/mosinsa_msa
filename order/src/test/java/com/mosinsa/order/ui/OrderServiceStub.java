package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.OrderService;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.query.application.dto.OrderDetail;

public class OrderServiceStub implements OrderService {
	@Override
	public OrderDetail order(OrderConfirmDto orderConfirmDto) {
		return null;
	}

	@Override
	public OrderDetail cancelOrder(String orderId) {
		return null;
	}
}
