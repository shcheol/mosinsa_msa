package com.mosinsa.order.infra.api.feignclient.product;

import com.mosinsa.order.command.application.NotEnoughProductStockException;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
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
public class ProductFeignAdapter implements ProductAdapter {

    private final ProductClient productClient;

    @Override
    public List<OrderProductDto> confirm(Map<String, Collection<String>> headers, OrderConfirmRequest orderConfirmRequest) {
        Map<String, Integer> orderQuantityMap = getOrderQuantityMap(orderConfirmRequest);

        return getProductResponses(headers, orderConfirmRequest).stream()
                .map(productResponseResponseResult -> {
                    ProductResponse productResponse = productResponseResponseResult.orElseThrow();
                    int orderStock = orderQuantityMap.getOrDefault(productResponse.productId(), Integer.MAX_VALUE);
                    if (productResponse.currentStock() < orderStock) {
                        log.info("not enough product stock {}/{}", orderStock, productResponse.currentStock());
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


    @Override
    public ResponseResult<Void> orderProducts(Map<String, Collection<String>> headers, String orderId, CreateOrderRequest orderRequest) {

        OrderProductRequests orderProductRequests = new OrderProductRequests(orderId, orderRequest.orderConfirm().orderProducts().stream()
                .map(op -> new OrderProductRequest(op.productId(), op.quantity())).toList());

        return ResponseResult.execute(() -> productClient.orderProducts(headers, orderProductRequests));
    }
}
