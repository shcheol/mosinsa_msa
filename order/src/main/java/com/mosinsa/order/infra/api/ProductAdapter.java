package com.mosinsa.order.infra.api;

import com.mosinsa.order.infra.api.httpinterface.product.OrderProductRequests;

public interface ProductAdapter {

    ResponseResult<Void> orderProducts(String orderId, OrderProductRequests orderRequest);
}
