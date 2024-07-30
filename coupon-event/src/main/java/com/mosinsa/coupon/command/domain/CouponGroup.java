package com.mosinsa.coupon.command.domain;

import com.mosinsa.promotion.command.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class CouponGroup extends BaseEntity {

	private long couponGroupSequence;

	private long minUsePrice;

	private LocalDate duringDate;

	@Enumerated(EnumType.STRING)
	private DiscountPolicy discountPolicy;

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
