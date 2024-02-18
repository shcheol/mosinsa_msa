package com.mosinsa.order.infra.feignclient;

public class CouponServiceNotAvailableException extends RuntimeException {
    public CouponServiceNotAvailableException(String message) {
        super(message);
    }
}
