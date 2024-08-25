package com.mosinsa.order.infra.api;

import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.OrderConfirmRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductAdapter {
    List<OrderProductDto> confirm(OrderConfirmRequest orderConfirmRequest);

    ResponseResult<Void> orderProducts(String orderId, CreateOrderRequest orderRequest);
}
