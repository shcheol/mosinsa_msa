package com.mosinsa.order.ui.request;

import com.mosinsa.order.command.domain.OrderStatus;

public record SearchCondition(String customerId, OrderStatus status) {
}
