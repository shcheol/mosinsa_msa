package com.mosinsa.coupon.command.domain;

public class InvalidCouponStateException extends RuntimeException {

	public InvalidCouponStateException(String message) {
		super(message);
	}
}
