package com.mosinsa.order.infra.api;

import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.OrderConfirmRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductAdapter {
    List<OrderProductDto> confirm(Map<String, Collection<String>> headers, OrderConfirmRequest orderConfirmRequest);

    ResponseResult<Void> orderProducts(Map<String, Collection<String>> headers, String orderId, CreateOrderRequest orderRequest);
}
