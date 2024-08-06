package com.mosinsa.coupon.command.domain;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.domain.PromotionId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "coupon")
@Getter
public class Coupon {

	@EmbeddedId
	private CouponId id;
	@Embedded
	private PromotionId promotionId;
	private LocalDateTime issuedDate;
	private String memberId;
	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private CouponState state;
	@Embedded
	private CouponDetails details;

	public static Coupon create(PromotionId promotionId, CouponDetails details) {
		Coupon coupon = new Coupon();
		coupon.id = CouponId.newId();
		coupon.promotionId = promotionId;
		coupon.details = details;
		coupon.state = CouponState.CREATED;
		return coupon;
	}

	public static List<Coupon> createAll(PromotionId promotionId, int quantity, CouponDetails details) {
		if (quantity <= 0) throw new CouponException(CouponError.INVALID_QUANTITY);
		ArrayList<Coupon> coupons = new ArrayList<>();
		for (int i = 0; i < quantity; i++) {
			coupons.add(Coupon.create(promotionId, details));
		}
		return coupons;
	}

	public void issueForMember(String memberId) {
		this.memberId = memberId;
		this.issuedDate = LocalDateTime.now();
		this.state = CouponState.ISSUED;
	}

	public void use() {
		verifyCanUseState();
		this.state = CouponState.USED;
	}

	public void useCancel() {
		verifyUsedCoupon();
		this.state = CouponState.ISSUED;
	}

	private void verifyUsedCoupon() {
		if (this.state != CouponState.USED) {
			throw new InvalidCouponStateException("사용하지 않은 쿠폰");
		}
	}

	private void verifyCanUseState() {
		if (this.state != CouponState.ISSUED) {
			throw new InvalidCouponStateException("이미 사용한 쿠폰입니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Coupon coupon)) return false;
		return Objects.equals(id, coupon.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
