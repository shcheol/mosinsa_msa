package com.mosinsa.order.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderCoupon extends IdBaseEntity {

	private String couponId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	public static OrderCoupon of(String couponId, Order order){
		OrderCoupon orderCoupon = new OrderCoupon();
		orderCoupon.couponId = couponId;
		orderCoupon.order = order;
		return orderCoupon;
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
