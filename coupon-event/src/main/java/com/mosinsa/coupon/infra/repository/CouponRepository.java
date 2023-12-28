package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.domain.Coupon;
import com.mosinsa.coupon.domain.CouponId;
import com.mosinsa.promotion.domain.PromotionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, CouponId>, CustomCouponRepository, JdbcCouponRepository {

	long countCouponsByPromotionIdAndMemberIdIsNull(PromotionId promotionId);

}
