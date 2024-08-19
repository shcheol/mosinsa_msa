package com.mosinsa.promotion.infra.api;

import com.mosinsa.promotion.infra.api.feignclient.order.OrderSummary;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OrderAdapter {
    ResponseResult<List<OrderSummary>> getMyOrders(String customerId);
}
