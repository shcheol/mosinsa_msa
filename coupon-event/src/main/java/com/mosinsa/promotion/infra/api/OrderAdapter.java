package com.mosinsa.promotion.infra.api;

import com.mosinsa.promotion.infra.api.httpinterface.OrderSummary;

import java.util.List;

public interface OrderAdapter {
	ResponseResult<List<OrderSummary>> getMyOrders(String customerId);
}
