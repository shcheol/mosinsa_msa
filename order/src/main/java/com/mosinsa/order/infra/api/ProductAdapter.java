package com.mosinsa.order.infra.api;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.ui.request.OrderConfirmRequest;

import java.util.List;

public interface ProductAdapter {
    List<OrderProductDto> confirm(OrderConfirmRequest orderConfirmRequest);

    ResponseResult<Void> orderProducts(String orderId, OrderConfirmDto orderRequest);
}
