package com.mosinsa.order.command.application.dto;

import com.mosinsa.common.argumentresolver.CustomerInfo;

public record CancelOrderInfo(CustomerInfo customerInfo, String orderId) {
}
