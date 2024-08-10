package com.mosinsa.coupon.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coupon")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Coupon {

	@EmbeddedId
	private CouponId id;
	private String memberId;
	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private CouponState state;
	@Embedded
	private CouponCondition details;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime issuedDate;

	public static Coupon issue(String memberId, CouponCondition details) {
		Coupon coupon = new Coupon();
		coupon.id = CouponId.newId();
		coupon.memberId = memberId;
		coupon.details = details;
		coupon.state = CouponState.ISSUED;
		return coupon;
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
