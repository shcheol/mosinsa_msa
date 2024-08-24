package com.mosinsa.promotion.command.domain;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CouponGroupInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Quest quest;

    private long couponGroupSequence;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    public static CouponGroupInfo of(long couponGroupSequence, int quantity, DiscountPolicy discountPolicy, Quest quest) {
        CouponGroupInfo couponGroupInfo = new CouponGroupInfo();
        couponGroupInfo.couponGroupSequence = couponGroupSequence;
        couponGroupInfo.quantity = quantity;
        couponGroupInfo.discountPolicy = discountPolicy;
        couponGroupInfo.setQuest(quest);
        return couponGroupInfo;
    }

    private void setQuest(Quest quest) {
        this.quest = quest;
        this.quest.getCouponGroupInfoList().add(this);
    }

    public void issue() {
        quantity -= 1;
        if (quantity < 0) {
            throw new CouponException(CouponError.EMPTY_STOCK);
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
