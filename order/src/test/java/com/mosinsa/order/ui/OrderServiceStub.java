package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.OrderService;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.domain.*;
import com.mosinsa.order.infra.api.ExternalServerException;
import com.mosinsa.order.query.application.dto.OrderDetail;

import java.util.List;

public class OrderServiceStub implements OrderService {
	@Override
	public OrderDetail order(OrderConfirmDto orderConfirmDto) {
		ShippingInfo of = ShippingInfo.of(Address.of("", "", ""), Receiver.of("", ""), "");
		Order order = Order.create(OrderId.of("orderId"), "customer", List.of(OrderProduct.of("b", 100, 2)), of, 10000);
		order.useCoupon("q");
		return new OrderDetail(order);
	}

	@Override
	public OrderDetail cancelOrder(String orderId) {
		if (orderId.equals("exception")){
			throw new RuntimeException();
		}

		if (orderId.equals("externalServerException")){
			throw new ExternalServerException(500,"externalServerException");
		}

		ShippingInfo of = ShippingInfo.of(Address.of("", "", ""), Receiver.of("", ""), "");
		Order order = Order.create(OrderId.of("orderId"), "customer", List.of(OrderProduct.of("b", 100, 2)), of, 10000);
		order.useCoupon("q");
		return new OrderDetail(order);
	}
}
