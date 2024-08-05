package com.mosinsa.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Getter
public class CouponDetails {

    private LocalDateTime duringDate;

    @Column(name = "discount_policy")
    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

	public static CouponDetails of(LocalDateTime duringDate,DiscountPolicy discountPolicy){
		CouponDetails couponDetails = new CouponDetails();
		couponDetails.duringDate = duringDate;
		couponDetails.discountPolicy = discountPolicy;
		return couponDetails;
	}

    public static CouponDetails createOneYearDuringDate(DiscountPolicy discountPolicy){
        CouponDetails couponDetails = new CouponDetails();
        couponDetails.duringDate = getYearAfter();
        couponDetails.discountPolicy = discountPolicy;
        return couponDetails;
    }

    private static LocalDateTime getYearAfter(){
        return LocalDateTime.now().plusYears(1);
    }

	protected CouponDetails() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CouponDetails that = (CouponDetails) o;
		return Objects.equals(duringDate, that.duringDate) && discountPolicy == that.discountPolicy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duringDate, discountPolicy);
	}
}
