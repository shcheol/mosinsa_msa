package com.mosinsa.couponissue.dto;

import com.mosinsa.couponissue.entity.CouponEvent;
import com.mosinsa.couponissue.entity.DiscountPolicy;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CouponEventDto {

    private Long id;

    private String eventName;

    private DiscountPolicy discountPolicy;

    private int quantity;

    private LocalDateTime eventStartAt;

    private LocalDateTime couponExpiryDate;

    public CouponEventDto(CouponEvent couponEvent) {
        this.id = couponEvent.getId();
        this.eventName = couponEvent.getEventName();
        this.discountPolicy = couponEvent.getDiscountPolicy();
        this.quantity = couponEvent.getQuantity();
        this.eventStartAt = couponEvent.getEventStartAt();
        this.couponExpiryDate = couponEvent.getCouponExpiryDate();
    }

}
