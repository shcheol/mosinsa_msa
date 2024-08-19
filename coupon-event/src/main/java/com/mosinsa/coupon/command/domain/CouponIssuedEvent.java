package com.mosinsa.coupon.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CouponIssuedEvent {
	private final String memberId;
	private final Long minUsePrice;
	private final LocalDate duringDate;
	private final DiscountPolicy discountPolicy;
}
