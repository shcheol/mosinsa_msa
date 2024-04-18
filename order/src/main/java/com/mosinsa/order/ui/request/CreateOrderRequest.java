package com.mosinsa.order.ui.request;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;

public record CreateOrderRequest(OrderConfirmDto orderConfirm) {

}
