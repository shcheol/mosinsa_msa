package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.domain.Coupon;
import com.mosinsa.coupon.domain.CouponId;
import com.mosinsa.coupon.dto.CouponDto;
import com.mosinsa.coupon.dto.CouponSearchCondition;

import java.util.List;
import java.util.Optional;

public interface CustomCouponRepository {

    Optional<Coupon> findNotIssuedCoupon(CouponSearchCondition condition);

	List<CouponId> findCouponsInPromotion(CouponSearchCondition condition);

	List<CouponDto> findMyCoupons(String memberId);

    CouponId findAssignedCoupon(CouponSearchCondition condition);
}
