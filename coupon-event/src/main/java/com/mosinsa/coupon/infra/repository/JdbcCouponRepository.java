package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.domain.Coupon;

import java.util.List;

public interface JdbcCouponRepository {

    int batchInsert(List<Coupon> coupon);
}
