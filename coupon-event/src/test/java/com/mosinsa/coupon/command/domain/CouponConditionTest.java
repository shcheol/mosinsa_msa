package com.mosinsa.coupon.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CouponConditionTest {

    LocalDate date = LocalDate.of(2023, 10, 28);

    @Test
    void equalsAndHashCode() {
        CouponCondition couponCondition1 = CouponCondition.of(3000L, date, DiscountPolicy.NONE);
        CouponCondition couponCondition2 = CouponCondition.of(3000L, date, DiscountPolicy.NONE);
        CouponCondition couponCondition3 = CouponCondition.of(3000L, date.plusDays(3), DiscountPolicy.NONE);
        CouponCondition couponCondition4 = CouponCondition.of(3000L, date, DiscountPolicy.TEN_PERCENTAGE);
        CouponCondition couponCondition = new CouponCondition();

        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(couponCondition1, couponCondition2, couponCondition, couponCondition3, couponCondition4);
        assertThat(b).isTrue();
    }
}