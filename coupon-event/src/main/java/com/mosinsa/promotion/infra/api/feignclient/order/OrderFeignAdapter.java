package com.mosinsa.promotion.infra.api.feignclient.order;

import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderFeignAdapter implements OrderAdapter {

//    private final OrderClient client;


    @Override
    public ResponseResult<OrderSummary> getMyOrders(Map<String, Collection<String>> headers) {
        return null;
    }
}
