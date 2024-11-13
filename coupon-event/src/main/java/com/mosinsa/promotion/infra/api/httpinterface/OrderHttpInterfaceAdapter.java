package com.mosinsa.promotion.infra.api.httpinterface;

import com.mosinsa.promotion.infra.api.CustomPageImpl;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderHttpInterfaceAdapter implements OrderAdapter {

    private final OrderServiceHttpClient orderServiceHttpClient;

    @Override
    public ResponseResult<CustomPageImpl<OrderSummary>> getMyOrders(String customerId) {
        return ResponseResult.executeForResponseEntity(() -> orderServiceHttpClient.myOrders(customerId));
    }
}
