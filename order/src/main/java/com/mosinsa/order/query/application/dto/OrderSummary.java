package com.mosinsa.order.query.application.dto;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderStatus;

public record OrderSummary(
		String id,
		String customerId,
		int totalPrice,
		OrderStatus status) {
	public static OrderSummary of(Order order) {
		return new OrderSummary(
				order.getId().getId(),
				order.getCustomerId(),
				order.getTotalPrice().getValue(),
				order.getStatus()
		);
	}

}
