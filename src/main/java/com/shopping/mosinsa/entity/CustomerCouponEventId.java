package com.shopping.mosinsa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerCouponEventId implements Serializable {
    private Customer customer;

    private CouponEvent couponEvent;

}