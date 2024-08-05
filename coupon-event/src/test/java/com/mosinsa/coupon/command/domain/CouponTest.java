package com.mosinsa.coupon.command.domain;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.domain.PromotionId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    void createAllInvalidQuantity(){
        PromotionId promotionId = PromotionId.of("promotionTest2");
        CouponDetails details = CouponDetails.of(
                LocalDateTime.of(2024, 10, 28, 00, 00),
                DiscountPolicy.TEN_PERCENTAGE);

        assertThrows(CouponException.class, () -> Coupon.createAll(promotionId, 0, details));
    }



}