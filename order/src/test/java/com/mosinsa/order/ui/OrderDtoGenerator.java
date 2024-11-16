package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.*;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class OrderDtoGenerator {
	public static OrderDetail orderDetailForTest() {

		ShippingInfo shippingInfo = ShippingInfo.of(Address.of("", "", ""),
				Receiver.of("", ""), "");

		OrderProduct op = OrderProduct.of("b", "",10000, 1, 10000);
		ReflectionTestUtils.setField(op, "id", 1L);
		return new OrderDetail(OrderId.newId().getId(), "customer", 10000, OrderStatus.PAYMENT_WAITING,
				ShippingInfoDto.of(shippingInfo), List.of(OrderDetail.OrderProductDetail.of(op)));
	}

	public static OrderSummary orderSummaryForTest() {
		return new OrderSummary("id1","customer",10000, OrderStatus.PAYMENT_WAITING);
	}
}
