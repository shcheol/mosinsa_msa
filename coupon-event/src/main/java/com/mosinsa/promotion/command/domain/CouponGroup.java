package com.mosinsa.promotion.command.domain;

import com.mosinsa.coupon.command.domain.DiscountPolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
public class CouponGroup extends BaseEntity{

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

}
