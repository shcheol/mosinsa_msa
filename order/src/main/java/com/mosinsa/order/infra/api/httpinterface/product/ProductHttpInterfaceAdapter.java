package com.mosinsa.order.infra.api.httpinterface.product;

import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductHttpInterfaceAdapter implements ProductAdapter {

	private final ProductHttpClient productHttpClient;

	@Override
	public ResponseResult<Void> orderProducts(String orderId, OrderProductRequests orderRequest) {
		return ResponseResult.executeForResponseEntity(() -> productHttpClient.orderProducts(orderRequest));
	}
}
