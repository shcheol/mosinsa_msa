package com.mosinsa.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CouponDetails {

    private LocalDateTime duringDate;

    @Column(name = "discount_policy")
    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    public static CouponDetails createOneYearDuringDate(DiscountPolicy discountPolicy){
        CouponDetails couponDetails = new CouponDetails();
        couponDetails.duringDate = getYearAfter();
        couponDetails.discountPolicy = discountPolicy;
        return couponDetails;
    }

    private static LocalDateTime getYearAfter(){
        return LocalDateTime.now().plusYears(1);
    }

}
