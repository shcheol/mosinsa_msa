package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.command.domain.Coupon;

import java.util.List;

public interface CustomCouponRepository {

	List<Coupon> findMyCoupons(String memberId);
}
