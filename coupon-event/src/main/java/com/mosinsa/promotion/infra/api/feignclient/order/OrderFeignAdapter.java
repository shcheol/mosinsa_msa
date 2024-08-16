package com.mosinsa.promotion.infra.api.feignclient.order;

import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
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
    public ResponseResult<List<OrderSummary>> getMyOrders(Map<String, Collection<String>> headers) {
		ResponseResult<List<OrderSummary>> execute = ResponseResult.execute(() -> client.myOrders(headers));
		return execute;
	}
}
