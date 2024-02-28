package com.mosinsa.order.application.dto;

import com.mosinsa.order.domain.Order;
import com.mosinsa.order.domain.OrderStatus;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class OrderDetailDto {

	String orderId;
	String customerId;
	String couponId;
	int totalPrice;
	OrderStatus status;

	List<OrderProductDto> orderProducts = new ArrayList<>();

	public OrderDetailDto(Order order) {
		this.orderId = order.getId().getId();
		this.customerId = order.getCustomerId();
		this.couponId = order.getCouponId();
		this.status = order.getStatus();
		this.totalPrice = order.getTotalPrice().getValue();
		this.orderProducts.addAll(
				order.getOrderProducts().stream().map(OrderProductDto::new).toList());
	}

}
