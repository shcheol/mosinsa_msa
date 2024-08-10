package com.mosinsa.coupon.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Getter
public class CouponCondition {

    private Long minUsePrice;
    private LocalDate duringDate;
    @Column(name = "discount_policy")
    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    public static CouponCondition of(Long minUsePrice, LocalDate duringDate, DiscountPolicy discountPolicy) {
        CouponCondition couponCondition = new CouponCondition();
        couponCondition.minUsePrice = minUsePrice;
        couponCondition.duringDate = duringDate;
        couponCondition.discountPolicy = discountPolicy;
        return couponCondition;
    }

    protected CouponCondition() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponCondition that = (CouponCondition) o;
        return Objects.equals(duringDate, that.duringDate) && discountPolicy == that.discountPolicy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(duringDate, discountPolicy);
    }
}
