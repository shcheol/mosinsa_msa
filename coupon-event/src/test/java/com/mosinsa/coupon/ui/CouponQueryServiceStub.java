package com.mosinsa.coupon.ui;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.query.application.CouponQueryService;
import com.mosinsa.coupon.query.application.dto.CouponDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CouponQueryServiceStub implements CouponQueryService {
    CouponCondition of = CouponCondition.of(8000L, LocalDate.now(), DiscountPolicy.NONE);

    @Override
    public CouponDetails getCouponDetails(String couponId) {
        if (couponId.equals("globalEx")){
            throw new IllegalArgumentException();
        }
        if (couponId.equals("id4xx")){
            throw new CouponException(CouponError.NOT_FOUND);
        }
        if (couponId.equals("id5xx")){
            throw new CouponException(CouponError.INTERNAL_SERVER_ERROR);
        }

        return new CouponDetails(
                couponId, LocalDateTime.now(),null, CouponState.ISSUED, of);
    }

    @Override
    public List<CouponDetails> getMyCoupons(String memberId) {

        Coupon coupon1 = Coupon.issue(memberId, of);
        Coupon coupon2 = Coupon.issue(memberId, of);

        CouponDetails couponDto1 = CouponDetails.convert(coupon1);
        CouponDetails couponDto2 = CouponDetails.convert(coupon2);

        return List.of(couponDto1, couponDto2);
    }
}
