package com.mosinsa.coupon.command.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CouponGroupRepository extends Repository<CouponGroup, Long> {

	Optional<CouponGroup> findById(Long id);

	CouponGroup save(CouponGroup couponGroup);
}
