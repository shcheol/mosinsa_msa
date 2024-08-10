package com.mosinsa.coupon.query.application;

import com.mosinsa.coupon.query.application.dto.CouponDetails;

import java.util.List;

public interface CouponQueryService {

    CouponDetails getCouponDetails(String couponId);
    List<CouponDetails> getMyCoupons(String memberId);
}
