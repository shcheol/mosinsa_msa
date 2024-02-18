package com.mosinsa.order.infra.feignclient.coupon;

public class InvalidCouponException extends RuntimeException {
    public InvalidCouponException(String message) {
        super(message);
    }
}
