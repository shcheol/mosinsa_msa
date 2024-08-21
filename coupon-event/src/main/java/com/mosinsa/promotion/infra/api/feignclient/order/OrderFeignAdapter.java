package com.mosinsa.promotion.infra.api.feignclient.order;

import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
import com.mosinsa.promotion.infra.api.feignclient.RequestHeaderExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderFeignAdapter implements OrderAdapter {

	private final OrderClient client;


	@Override
	public ResponseResult<List<OrderSummary>> getMyOrders(String customerId) {

		Map<String, Collection<String>> headers = RequestHeaderExtractor.extract();
		return ResponseResult.execute(() -> client.myOrders(headers, customerId).getContent());
	}
}
