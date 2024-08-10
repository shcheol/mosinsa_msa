package com.mosinsa.coupon.command.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponCondition;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;
import com.mosinsa.coupon.infra.repository.CouponRepository;
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

    @Override
    public CouponId issue(CouponIssuedEvent event) {
        log.info("coupon issue event {}", event);
        return couponRepository.save(
                Coupon.issue(
                        event.getMemberId(),
                        CouponCondition.of(
                                event.getMinUsePrice(),
                                event.getDuringDate(),
                                event.getDiscountPolicy()
                        ))).getId();
    }

    @Override
    public void useCoupon(String couponId) {
        couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .use();
    }

    @Override
    public void rollbackCoupon(String couponId) {
        couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .useCancel();
    }


}
