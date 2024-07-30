package com.mosinsa.order.ui;

import com.mosinsa.order.command.domain.*;
import com.mosinsa.order.query.application.OrderQueryService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class OrderQueryServiceStub implements OrderQueryService {
	@Override
	public Page<OrderSummary> findMyOrdersByCondition(SearchCondition condition, Pageable pageable) {
		Address address = Address.of("zipCode", "address1", "address2");
		Receiver receiver = Receiver.of("myname", "010-xxx-xxxx");
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		return new PageImpl<>(
				List.of(new OrderSummary(Order.create(OrderId.newId(), "customer", "", List.of(OrderProduct.of("b", 100, 2)),
								shippingInfo, 10000)),
						new OrderSummary(Order.create(OrderId.newId(), "customer", "", List.of(OrderProduct.of("b", 100, 2)),
								shippingInfo, 10000)))

		);
	}

	@Override
	public OrderDetail getOrderDetails(String orderId) {
		ShippingInfo of = ShippingInfo.of(Address.of("", "", ""), Receiver.of("", ""), "");
		return new OrderDetail(
				Order.create(
						OrderId.of(orderId),
						"customer", "", List.of(OrderProduct.of("b", 100, 2)),
						of, 10000));
	}
}
