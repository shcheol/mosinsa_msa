package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.promotion.domain.PromotionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, CouponId>, CustomCouponRepository, JdbcCouponRepository {

	long countCouponsByPromotionIdAndMemberIdIsNull(PromotionId promotionId);

}
