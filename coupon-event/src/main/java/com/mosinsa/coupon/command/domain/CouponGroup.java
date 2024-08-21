package com.mosinsa.coupon.command.domain;

import com.mosinsa.promotion.command.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class CouponGroup extends BaseEntity {

	private String name;

	private long minUsePrice;

	private LocalDate duringDate;

	@Enumerated(EnumType.STRING)
	private DiscountPolicy discountPolicy;

	public static CouponGroup of(String name, long minUsePrice, LocalDate duringDate, DiscountPolicy discountPolicy) {
		CouponGroup couponGroup = new CouponGroup();
		couponGroup.name = name;
		couponGroup.minUsePrice = minUsePrice;
		couponGroup.duringDate = duringDate;
		couponGroup.discountPolicy = discountPolicy;
		return couponGroup;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
