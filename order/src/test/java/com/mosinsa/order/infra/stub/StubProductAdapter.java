package com.mosinsa.order.infra.stub;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.ui.request.OrderConfirmRequest;

import java.util.List;

public class StubProductAdapter implements ProductAdapter {
	@Override
	public List<OrderProductDto> confirm(OrderConfirmRequest orderConfirmRequest) {

		return List.of(
				OrderProductDto.builder()
						.price(1000)
						.productId("productId")
						.quantity(2)
						.amounts(2000)
						.build()
		);
	}

	@Override
	public ResponseResult<Void> orderProducts(String orderId, OrderConfirmDto orderConfirmDto) {
		return ResponseResult.execute(() -> System.out.println("call product service"));
	}
}
