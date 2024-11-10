package com.mosinsa.order.command.application.dto;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderInfo(CustomerInfo customerInfo,
                        ShippingInfoDto shippingInfo,
                        List<OrderProductDto> orderProducts) {
}
