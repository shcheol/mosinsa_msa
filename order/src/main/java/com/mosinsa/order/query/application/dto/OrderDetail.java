package com.mosinsa.order.query.application.dto;

import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.*;

import java.util.List;

public record OrderDetail(
		String id,
		String customerId,
		int totalPrice,
		OrderStatus status,
		ShippingInfoDto shippingInfo,
		List<OrderProductDetail> orderProducts) {

	public static OrderDetail of(Order order) {
		return new OrderDetail(
				order.getId().getId(),
				order.getCustomerId(),
				order.getTotalPrice().getValue(),
				order.getStatus(),
				ShippingInfoDto.of(order.getShippingInfo()),
				order.getOrderProducts().stream().map(OrderProductDetail::of).toList()
		);
	}

	public record OrderProductDetail(long id, String name, int price, int quantity, int amounts,
									 List<ProductOptionsDto> productOptions, List<OrderCouponDto> orderCoupon) {

		public static OrderProductDetail of(OrderProduct orderProduct) {
			return new OrderProductDetail(
					orderProduct.getId(),
					orderProduct.getName(),
					orderProduct.getPrice().getValue(),
					orderProduct.getQuantity(),
					orderProduct.getAmounts().getValue(),
					orderProduct.getProductOptions().stream().map(ProductOptionsDto::of).toList(),
					orderProduct.getOrderCoupon().stream().map(OrderCouponDto::of).toList()
			);
		}

		public record ProductOptionsDto(long id, long option_id, String name) {
			public static ProductOptionsDto of(ProductOption productOptions) {
				return new ProductOptionsDto(productOptions.getId(), productOptions.getOptionId(), productOptions.getName());
			}
		}

		public record OrderCouponDto(long id, String couponId, DiscountPolicy discountPolicy) {
			public static OrderCouponDto of(OrderCoupon orderCoupon) {
				return new OrderCouponDto(orderCoupon.getId(), orderCoupon.getCouponId(), orderCoupon.getDiscountPolicy());
			}
		}

	}

}
