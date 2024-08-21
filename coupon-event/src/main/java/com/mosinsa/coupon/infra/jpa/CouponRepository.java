package com.mosinsa.coupon.infra.jpa;

import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, CouponId>, CustomCouponRepository {

}
