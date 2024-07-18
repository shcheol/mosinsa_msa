package com.mosinsa.order.command.application.dto;

import com.mosinsa.order.command.domain.DiscountPolicy;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderConfirmDto(String couponId, String customerId,
                              List<OrderProductDto> orderProducts, ShippingInfoDto shippingInfo, int totalAmount) {

    public OrderConfirmDto useCoupon(String couponId, DiscountPolicy discountPolicy) {
        int discountedAmount = discountPolicy.applyDiscountPrice(totalAmount);

        return OrderConfirmDto.builder()
                .couponId(couponId)
                .customerId(customerId)
                .orderProducts(orderProducts)
                .totalAmount(totalAmount - discountedAmount)
                .shippingInfo(shippingInfo)
                .build();
    }
}
