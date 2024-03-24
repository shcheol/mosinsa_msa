package com.mosinsa.order.infra.feignclient.product;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.MyOrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final ProductClient productClient;

    public void orderProduct(Map<String, Collection<String>> headers, CreateOrderRequest orderRequest) {
        ResponseResult<Void> execute = ResponseResult.execute(() ->
                productClient.orderProducts(headers,
                        new OrderProductRequests(
                                orderRequest.getMyOrderProducts().stream().map(op ->
                                        new OrderProductRequest(op.productId(), op.quantity())
                                ).toList())));
        execute.orElseThrow();
    }


    public void cancelOrderProduct(Map<String, Collection<String>> headers, List<MyOrderProduct> orderProducts) {
        ResponseResult<Void> execute = ResponseResult.execute(() ->
                productClient.cancelOrderProducts(headers,
                        new CancelOrderProductRequests(
                                orderProducts.stream().map(op ->
                                        new CancelOrderProductRequest(op.productId(), op.quantity())
                                ).toList())));
        execute.orElseThrow();
    }
}
