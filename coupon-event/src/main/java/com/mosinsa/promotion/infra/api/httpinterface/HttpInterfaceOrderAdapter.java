package com.mosinsa.promotion.infra.api.httpinterface;

import com.mosinsa.promotion.infra.api.CustomPageImpl;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HttpInterfaceOrderAdapter implements OrderAdapter {

	private final OrderServiceHttpClient orderServiceHttpClient;

	@Override
	public ResponseResult<List<OrderSummary>> getMyOrders(String customerId) {
		ResponseEntity<CustomPageImpl<OrderSummary>> customPageResponseEntity = orderServiceHttpClient.myOrders(customerId);



		return ResponseResult.execute(() -> orderServiceHttpClient
				.myOrders(customerId)
				.getBody()
				.getContent());
	}
}
