package com.mosinsa.order.infra.api;

import com.mosinsa.order.infra.api.feignclient.product.OrderProductRequests;

public interface ProductAdapter {

    ResponseResult<Void> orderProducts(String orderId, OrderProductRequests orderRequest);
}
