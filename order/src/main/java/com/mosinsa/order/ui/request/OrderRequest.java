package com.mosinsa.order.ui.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderRequest(ShippingInfoDto shippingInfo,
						   List<MyOrderProduct> myOrderProducts) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record MyOrderProduct(String id, int stock, String name, List<ProductOptionsDto> options, CouponDto coupon, int perPrice, int totalPrice) {

		public record ProductOptionsDto(Long id, String name){

		}

		public record CouponDto(String id){

		}

	}

}
