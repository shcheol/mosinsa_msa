package com.shopping.mosinsa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends AuditingEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_event_id")
    private CouponEvent couponEvent;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    private LocalDateTime expirationPeriod;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    public void issuanceCouponToCustomer(Customer customer){
        Assert.isNull(this.customer,"이미 할당된 쿠폰");
        this.customer = customer;
    }

    public static Coupon createCoupon(CouponEvent couponEvent, DiscountPolicy discountPolicy, LocalDateTime expirationPeriod) {

        Assert.isTrue(expirationPeriod.isAfter(LocalDateTime.now()), "기간이 이미지난 쿠폰은 생성할 수 없습니다.");

        Coupon coupon = new Coupon();
        coupon.couponEvent = couponEvent;
        coupon.discountPolicy = discountPolicy;
        coupon.expirationPeriod = expirationPeriod;
        coupon.status = CouponStatus.UNUSED;

        return coupon;
    }
}
