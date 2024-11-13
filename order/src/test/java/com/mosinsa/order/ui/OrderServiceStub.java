package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.OrderService;
import com.mosinsa.order.command.application.dto.OrderInfo;
import com.mosinsa.order.infra.api.ExternalServerException;
import com.mosinsa.order.query.application.dto.OrderDetail;
import org.springframework.http.HttpStatusCode;

public class OrderServiceStub implements OrderService {


	@Override
	public OrderDetail order(OrderInfo orderInfo) {
		return OrderDtoGenerator.orderDetailForTest();
	}

	@Override
	public OrderDetail cancelOrder(String orderId) {
		if (orderId.equals("exception")) {
			throw new RuntimeException();
		}

		if (orderId.equals("externalServerException")) {
			throw new ExternalServerException(HttpStatusCode.valueOf(500), "externalServerException");
		}

		return OrderDtoGenerator.orderDetailForTest();
	}
}
