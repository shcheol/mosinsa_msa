package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.command.domain.OrderProduct;
import com.mosinsa.order.command.domain.ShippingInfo;
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
		ShippingInfoDto shippingInfoDto = new ShippingInfoDto("", new AddressDto("", "", ""), new ReceiverDto("", ""));
		return new PageImpl<>(
				List.of(new OrderSummary(Order.create(OrderId.newId(), "customer", "", List.of(OrderProduct.create("b", 100, 2)),
								ShippingInfo.of(shippingInfoDto), 10000)),
						new OrderSummary(Order.create(OrderId.newId(), "customer", "", List.of(OrderProduct.create("b", 100, 2)),
								ShippingInfo.of(shippingInfoDto), 10000)))

		);
	}

	@Override
	public OrderDetail getOrderDetails(String orderId) {
		return new OrderDetail(Order.create(OrderId.of(orderId), "customer", "", List.of(OrderProduct.create("b", 100, 2)),
				ShippingInfo.of(new ShippingInfoDto("", new AddressDto("", "", ""), new ReceiverDto("", ""))), 10000));
	}
}
