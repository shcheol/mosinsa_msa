package com.mosinsa.coupon.command.application;

import com.mosinsa.common.aop.Retry;
import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.query.application.dto.CouponDto;
import com.mosinsa.coupon.query.application.dto.CouponSearchCondition;
import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public void createAll(PromotionCreatedEvent event) {
        couponRepository.saveAll(
                Coupon.createAll(event.getPromotionId(), event.getQuantity(), event.getDetails()));
    }

    @Override
    public void createAllByBatchInsert(PromotionCreatedEvent event) {
        couponRepository.batchInsert(Coupon.createAll(event.getPromotionId(), event.getQuantity(), event.getDetails()));
    }

    @Override
    @Retry(times = 3)
    public void createForNewMember(String memberId) {
        checkDuplicateIssue(memberId);

        couponRepository.save(
                Coupon.create(null, CouponDetails.createOneYearDuringDate(DiscountPolicy.TEN_PERCENTAGE)))
                .issuedCoupon(memberId);
    }

    private void checkDuplicateIssue(String memberId) {
        List<CouponDto> myCoupons = couponRepository.findMyCoupons(memberId);
        if (!myCoupons.isEmpty()) {
            log.debug("duplicate issue request: throw CouponException");
            throw new CouponException(CouponError.DUPLICATE_PARTICIPATION);
        }
    }


    @Override
    public CouponId issue(CouponIssuedEvent event) {

        log.info("coupon issue event {}", event);
        CouponSearchCondition condition = new CouponSearchCondition(event.getMemberId(), event.getPromotionId());

        Coupon coupon = couponRepository.findNotIssuedCoupon(condition)
                .orElseThrow(() -> {
                    log.info("member {} {}", condition.memberId(), CouponError.EMPTY_STOCK.getMessage());
                    throw new CouponException(CouponError.EMPTY_STOCK);
                });
        coupon.issuedCoupon(event.getMemberId());

        log.info("member {} issue coupon", condition.memberId());

        return coupon.getId();
    }

    @Override
    public void useCoupon(String couponId) {
        couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .useCoupon();
    }

    @Override
    public void rollbackCoupon(String couponId) {
        couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .unUseCoupon();
    }


}
