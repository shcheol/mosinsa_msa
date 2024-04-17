package com.mosinsa.order.ui.request;

import com.mosinsa.order.command.application.dto.ShippingInfoDto;

import java.util.List;

public record CreateOrderRequest(String customerId, String couponId, ShippingInfoDto shippingInfo,
                                 List<MyOrderProduct> myOrderProducts) {

}
