package com.mosinsa.coupon.ui;

import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.coupon.command.domain.CouponId;

public class CouponServiceStub implements CouponService {

	private int issueCalledCount=0;
	private int cancelCalledCount=0;

	public int getCancelCalledCount() {
		return cancelCalledCount;
	}

	public int getIssueCalledCount() {
		return issueCalledCount;
	}

	@Override
	public CouponId issue(String memberId, long couponGroupSequence) {
		issueCalledCount++;
		return CouponId.of("id");
	}

	@Override
	public void useCoupon(String couponId) {

	}

	@Override
	public void cancelCoupon(String couponId) {
		cancelCalledCount++;
	}
}
