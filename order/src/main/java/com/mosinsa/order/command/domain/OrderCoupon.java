package com.mosinsa.order.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderCoupon extends BaseIdEntity {

	private String couponId;

	@Enumerated(EnumType.STRING)
	private DiscountPolicy discountPolicy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_product_id")
	private OrderProduct orderProduct;

	public static OrderCoupon of(String couponId, DiscountPolicy discountPolicy) {
		OrderCoupon orderCoupon = new OrderCoupon();
		orderCoupon.couponId = couponId;
		orderCoupon.discountPolicy = discountPolicy;
		return orderCoupon;
	}

	protected void setOrderProduct(OrderProduct orderProduct){
		this.orderProduct = orderProduct;
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
