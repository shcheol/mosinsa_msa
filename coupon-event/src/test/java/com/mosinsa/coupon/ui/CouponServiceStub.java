package com.mosinsa.coupon.ui;

import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.coupon.command.domain.CouponId;

public class CouponServiceStub implements CouponService {

	private int cancelCalledCount=0;

	public int getCancelCalledCount() {
		return cancelCalledCount;
	}

	@Override
	public CouponId issue(String memberId, long couponGroupSequence) {
		return null;
	}

	@Override
	public void useCoupon(String couponId) {

	}

	@Override
	public void cancelCoupon(String couponId) {
		cancelCalledCount++;
	}
}
