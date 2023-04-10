package com.shopping.mosinsa.entity;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "couponEvent", cascade = CascadeType.ALL)
    private Set<CustomerCouponEvent> customerCouponEvent = new HashSet<>();
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

    public Coupon issuanceCoupon(Customer customer) {

        Assert.isTrue(quantity>0,"수량이 부족합니다.");
        Assert.isTrue(coupons.size()>0,"수량이 부족합니다.");
        Assert.isTrue(customerCouponEvent.add(new CustomerCouponEvent(customer, this)),"이미 참여한 이벤트입니다.");

        Coupon coupon = this.getCoupons().get(--this.quantity);
        coupon.issuanceCouponToCustomer(customer);

        return coupon;
    }
}
