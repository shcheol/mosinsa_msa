package com.mosinsa.order.infra.stub;

import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.infra.api.feignclient.product.OrderProductRequests;

public class StubProductAdapter implements ProductAdapter {

    @Override
    public ResponseResult<Void> orderProducts(String orderId, OrderProductRequests orderConfirmDto) {
        return ResponseResult.execute(() -> System.out.println("call product service"));
    }
}
