package com.mosinsa.order.ui;

import com.mosinsa.order.query.application.OrderQueryService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class OrderQueryServiceStub implements OrderQueryService {
	@Override
	public Page<OrderSummary> findMyOrdersByCondition(SearchCondition condition, Pageable pageable) {

		return new PageImpl<>(
				List.of(OrderDtoGenerator.orderSummaryForTest(),
						OrderDtoGenerator.orderSummaryForTest()), PageRequest.of(0, 10), 0);
	}

	@Override
	public OrderDetail getOrderDetails(String orderId) {
		return OrderDtoGenerator.orderDetailForTest();
	}

}
