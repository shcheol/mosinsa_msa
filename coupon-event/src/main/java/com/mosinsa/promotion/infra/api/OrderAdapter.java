package com.mosinsa.promotion.infra.api;

import com.mosinsa.promotion.infra.api.httpinterface.OrderSummary;

public interface OrderAdapter {
    ResponseResult<CustomPageImpl<OrderSummary>> getMyOrders(String customerId);
}
