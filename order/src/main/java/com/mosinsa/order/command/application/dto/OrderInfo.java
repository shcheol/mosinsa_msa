package com.mosinsa.order.command.application.dto;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderInfo(CustomerInfo customerInfo,
                        ShippingInfoDto shippingInfo,
                        List<OrderProductInfo> orderProducts) {

	public record OrderProductInfo(String id, int quantity, int perPrice, int totalPrice, List<ProductOptionsDto> options, CouponDto coupon){
		public record ProductOptionsDto(Long id, String name){

		}

		public record CouponDto(String id){

		}
	}
}
