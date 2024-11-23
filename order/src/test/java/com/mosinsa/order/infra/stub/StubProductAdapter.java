package com.mosinsa.order.infra.stub;

import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.infra.api.httpinterface.product.OrderProductRequests;
import org.springframework.http.ResponseEntity;

public class StubProductAdapter implements ProductAdapter {

    @Override
    public ResponseResult<Void> orderProducts(String orderId, OrderProductRequests orderConfirmDto) {
        return ResponseResult.executeForResponseEntity(() -> ResponseEntity.ok().build());
    }
}
