package com.mosinsa.promotion.command.domain;

import com.mosinsa.coupon.command.domain.DiscountPolicy;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CouponGroup extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private Quest quest;

    private long couponGroupSequence;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

}
