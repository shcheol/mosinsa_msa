package com.mosinsa.order.ui.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderConfirmRequest(String couponId, ShippingInfoDto shippingInfo,
								  List<MyOrderProduct> myOrderProducts) {
}
