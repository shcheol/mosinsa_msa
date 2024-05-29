package com.mosinsa.coupon.domain;

public class InvalidCouponStateException extends RuntimeException {

	public InvalidCouponStateException(String message) {
		super(message);
	}
}
