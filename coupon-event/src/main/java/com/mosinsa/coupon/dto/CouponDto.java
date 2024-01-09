package com.mosinsa.coupon.dto;

import com.mosinsa.coupon.domain.Coupon;
import com.mosinsa.coupon.domain.CouponDetails;
import com.mosinsa.coupon.domain.CouponId;
import com.mosinsa.coupon.domain.CouponState;
import com.mosinsa.promotion.domain.PromotionId;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponDto {
    private final String couponId;
    private final String promotionId;
    private final LocalDateTime issuedDate;
    private final String memberId;
    private final CouponState state;
    private final CouponDetails details;

    @QueryProjection
    public CouponDto(CouponId couponId, PromotionId promotionId, LocalDateTime issuedDate, String memberId, CouponState state, CouponDetails details) {
        this.couponId = couponId.getId();
        this.promotionId = promotionId == null ? null : promotionId.getId();
        this.issuedDate = issuedDate;
        this.memberId = memberId;
        this.state = state;
        this.details = details;
    }

	public static CouponDto convert(Coupon coupon) {
		return new CouponDto(
				coupon.getCouponId(),
				coupon.getPromotionId(),
				coupon.getIssuedDate(),
				coupon.getMemberId(),
				coupon.getState(),
				coupon.getDetails()
		);
	}
}
