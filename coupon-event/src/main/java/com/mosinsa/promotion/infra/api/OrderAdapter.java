package com.mosinsa.promotion.infra.api;

import com.mosinsa.promotion.infra.api.feignclient.order.OrderSummary;

import java.util.Collection;
import java.util.Map;

public interface OrderAdapter {
    ResponseResult<OrderSummary> getMyOrders(Map<String, Collection<String>> headers);
}
