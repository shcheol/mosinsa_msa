package com.mosinsa.coupon.query.application;

import com.mosinsa.coupon.query.application.dto.CouponDto;

import java.util.List;

public interface CouponQueryService {

    CouponDto getCouponDetails(String couponId);

    long count(String promotionId);


    List<CouponDto> getMyCoupons(String memberId);
}
