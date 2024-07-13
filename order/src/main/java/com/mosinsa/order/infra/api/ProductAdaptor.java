package com.mosinsa.order.infra.api;

import com.mosinsa.order.command.application.NotEnoughProductStockException;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.feignclient.product.OrderProductRequest;
import com.mosinsa.order.infra.api.feignclient.product.OrderProductRequests;
import com.mosinsa.order.infra.api.feignclient.product.ProductClient;
import com.mosinsa.order.infra.api.feignclient.product.ProductResponse;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.MyOrderProduct;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductAdaptor {

    private final ProductClient productClient;

    public List<OrderProductDto> confirm(Map<String, Collection<String>> headers, OrderConfirmRequest orderConfirmRequest) {
        Map<String, Integer> orderQuantityMap = getOrderQuantityMap(orderConfirmRequest);

        return getProductResponses(headers, orderConfirmRequest).stream()
                .map(productResponseResponseResult -> {
                    ProductResponse productResponse = productResponseResponseResult.orElseThrow();
                    int orderStock = orderQuantityMap.getOrDefault(productResponse.productId(), Integer.MAX_VALUE);
                    if (productResponse.stock() < orderStock) {
                        log.info("not enough product stock {}/{}", orderStock, productResponse.stock());
                        throw new NotEnoughProductStockException();
                    }
                    return OrderProductDto.builder()
                            .price(productResponse.price())
                            .productId(productResponse.productId())
                            .quantity(orderStock)
                            .amounts(productResponse.price() * orderStock)
                            .build();
                }).toList();
    }

    private Map<String, Integer> getOrderQuantityMap(OrderConfirmRequest orderConfirmRequest) {
        return orderConfirmRequest.myOrderProducts().stream()
                .collect(Collectors.toMap(MyOrderProduct::productId, MyOrderProduct::quantity));
    }

    private List<ResponseResult<ProductResponse>> getProductResponses(Map<String, Collection<String>> headers, OrderConfirmRequest orderConfirmRequest) {
        return orderConfirmRequest.myOrderProducts().stream()
                .map(myOrderProduct ->
                        ResponseResult.execute(() -> productClient.getProduct(headers, myOrderProduct.productId()))
                ).toList();
    }

    public ResponseResult<ProductResponse> getProduct(Map<String, Collection<String>> headers, String productId) {
        return ResponseResult.execute(() -> productClient.getProduct(headers, productId));
    }

    public ResponseResult<Void> orderProducts(Map<String, Collection<String>> headers, String orderId, CreateOrderRequest orderRequest) {

        OrderProductRequests orderProductRequests = new OrderProductRequests(orderId, orderRequest.orderConfirm().orderProducts().stream()
                .map(op -> new OrderProductRequest(op.productId(), op.quantity())).toList());

        return ResponseResult.execute(() -> productClient.orderProducts(headers, orderProductRequests));
    }
}
