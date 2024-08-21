package com.mosinsa.coupon.command.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.infra.jpa.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

	private final CouponRepository couponRepository;
	private final CouponGroupRepository couponGroupRepository;

	@Override
	public CouponId issue(String memberId, long couponGroupSequence) {

		CouponGroup couponGroup = couponGroupRepository.findById(couponGroupSequence)
				.orElseThrow(() -> new CouponException(CouponError.NOT_FOUND));

		Coupon issue = Coupon.issue(memberId, CouponCondition.of(
				couponGroup.getMinUsePrice(),
				couponGroup.getDuringDate(),
				couponGroup.getDiscountPolicy()
		));

		return couponRepository.save(issue).getId();
	}

	@Override
	public void useCoupon(String couponId) {
		couponRepository.findById(CouponId.of(couponId))
				.orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
				.use();
	}

	@Override
	public void cancelCoupon(String couponId) {
		couponRepository.findById(CouponId.of(couponId))
				.orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
				.useCancel();
	}


}
