package com.mosinsa.order.infra.api.feignclient.product;

import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.RequestHeaderExtractor;
import com.mosinsa.order.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFeignAdapter implements ProductAdapter {

    private final ProductClient productClient;

    @Override
    public ResponseResult<Void> orderProducts(String orderId, OrderProductRequests orderProductRequests) {

        Map<String, Collection<String>> headers = RequestHeaderExtractor.extract();
        return ResponseResult.execute(() -> productClient.orderProducts(headers, orderProductRequests));
    }
}
