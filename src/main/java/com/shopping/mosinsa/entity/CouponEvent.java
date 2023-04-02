package com.shopping.mosinsa.entity;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_event_id")
    private Long id;

    private String eventName;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    private int quantity;

    @OneToMany(mappedBy = "couponEvent", cascade = CascadeType.ALL)
    private final List<Coupon> coupons = new ArrayList<>();

    private LocalDateTime eventStartAt;

    private LocalDateTime couponExpiryDate;


    public static CouponEvent createCouponEvent(CouponEventCreateRequest request){
        CouponEvent couponEvent = new CouponEvent();
        couponEvent.eventName = request.getEventName();
        couponEvent.discountPolicy = request.getDiscountPolicy();
        couponEvent.quantity = request.getQuantity();
        couponEvent.eventStartAt = request.getEventStartAt();
        couponEvent.couponExpiryDate = request.getExpiryDate();

        return couponEvent;
    }
}
