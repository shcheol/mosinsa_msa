package com.mosinsa.coupon.command.application;

import com.mosinsa.coupon.command.domain.CouponId;

public interface CouponService {

	CouponId issue(String memberId, long couponGroupSequence);

	void useCoupon(String couponId);

	void cancelCoupon(String couponId);
}
