package com.mosinsa.coupon.command.application;

import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;

public interface CouponService {

	CouponId issue(CouponIssuedEvent event);

	CouponId issue(String memberId, long couponGroupSequence);

	void useCoupon(String couponId);

	void rollbackCoupon(String couponId);
}
