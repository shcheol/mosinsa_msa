package com.mosinsa.order.infra.kafka;

import com.mosinsa.order.command.application.dto.OrderProductDto;

import java.util.List;

public record OrderCanceledEvent(String orderId, String customerId, String couponId, List<OrderProductDto> orderProducts) {
}
