package com.mosinsa.coupon.ui;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.query.application.CouponQueryService;
import com.mosinsa.coupon.query.application.dto.CouponDto;
import com.mosinsa.promotion.domain.PromotionId;

import java.time.LocalDateTime;
import java.util.List;

public class CouponQueryServiceStub implements CouponQueryService {
    @Override
    public CouponDto getCouponDetails(String couponId) {
        if (couponId.equals("globalEx")){
            throw new IllegalArgumentException();
        }
        if (couponId.equals("id4xx")){
            throw new CouponException(CouponError.NOT_FOUND);
        }
        if (couponId.equals("id5xx")){
            throw new CouponException(CouponError.INTERNAL_SERVER_ERROR);
        }

        return new CouponDto(CouponId.of(couponId), null, LocalDateTime.now(),null, CouponState.ISSUED,
                CouponDetails.of(LocalDateTime.now(), DiscountPolicy.NONE));
    }

    @Override
    public long count(String promotionId) {
        return 10;
    }

    @Override
    public List<CouponDto> getMyCoupons(String memberId) {

        Coupon coupon1 = Coupon.create(
                PromotionId.newId(), CouponDetails.of(LocalDateTime.now(), DiscountPolicy.TEN_PERCENTAGE));
        coupon1.issueForMember(memberId);
        Coupon coupon2 = Coupon.create(
                PromotionId.newId(), CouponDetails.of(LocalDateTime.now(), DiscountPolicy.TEN_PERCENTAGE));
        coupon2.issueForMember(memberId);

        CouponDto couponDto1 = CouponDto.convert(coupon1);
        CouponDto couponDto2 = CouponDto.convert(coupon2);

        return List.of(couponDto1, couponDto2);
    }
}
