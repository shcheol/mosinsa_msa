package com.shopping.mosinsa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@IdClass(CustomerCouponEventId.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CustomerCouponEvent {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_coupon_event_customer_id")
    private Customer customer;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_coupon_event_coupon_event_id")
    private CouponEvent couponEvent;


}