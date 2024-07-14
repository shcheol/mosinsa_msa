package com.mosinsa.order.infra.stub;

import com.mosinsa.order.command.application.NotEnoughProductStockException;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.OrderConfirmRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StubProductAdapter implements ProductAdapter {
    @Override
    public List<OrderProductDto> confirm(Map<String, Collection<String>> headers, OrderConfirmRequest orderConfirmRequest) {

        return List.of(
                OrderProductDto.builder()
                        .price(1000)
                        .productId("productId")
                        .quantity(2)
                        .amounts(2000)
                        .build()
        );
    }

    @Override
    public ResponseResult<Void> orderProducts(Map<String, Collection<String>> headers, String orderId, CreateOrderRequest orderRequest) {
        return ResponseResult.execute(() -> System.out.println("call product service"));
    }
}
