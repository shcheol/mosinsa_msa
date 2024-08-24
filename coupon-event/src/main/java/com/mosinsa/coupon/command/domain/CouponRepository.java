package com.mosinsa.coupon.command.domain;

import com.mosinsa.coupon.infra.jpa.CustomCouponRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CouponRepository extends Repository<Coupon, CouponId>, CustomCouponRepository {

	Coupon save(Coupon coupon);

	Optional<Coupon> findById(CouponId id);

}
