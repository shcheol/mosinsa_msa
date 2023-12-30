package com.mosinsa.coupon.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        LocalDateTime now = LocalDateTime.now();

        return LocalDateTime.of(LocalDate.of(now.getYear() + 1, now.getMonth(), now.getDayOfMonth()), LocalTime.NOON);
    }

}
