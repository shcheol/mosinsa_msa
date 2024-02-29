package com.mosinsa.coupon.application;

import com.mosinsa.common.aop.Retry;
import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.domain.*;
import com.mosinsa.coupon.application.dto.CouponDto;
import com.mosinsa.coupon.application.dto.CouponSearchCondition;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;
import com.mosinsa.promotion.domain.PromotionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public void createAll(PromotionCreatedEvent event) {
        couponRepository.saveAll(
                Coupon.createAll(event.getPromotionId(), event.getQuantity(), event.getDetails()));
    }

    public void createAllByBatchInsert(PromotionCreatedEvent event) {
        couponRepository.batchInsert(Coupon.createAll(event.getPromotionId(), event.getQuantity(), event.getDetails()));
    }

    @Retry(times = 3)
    public void createForNewMember(String memberId) {
        checkDuplicateIssue(memberId);

        couponRepository
                .save(Coupon.create(null,
                        CouponDetails.createOneYearDuringDate(DiscountPolicy.TEN_PERCENTAGE)))
                .issuedCoupon(memberId);
    }

    private void checkDuplicateIssue(String memberId) {
        List<CouponDto> myCoupons = couponRepository.findMyCoupons(memberId);
        if (!myCoupons.isEmpty()) {
            log.debug("duplicate issue request: throw CouponException");
            throw new CouponException(CouponError.DUPLICATE_PARTICIPATION);
        }
    }

    public long count(String promotionId) {
        return couponRepository.countCouponsByPromotionIdAndMemberIdIsNull(PromotionId.of(promotionId));
    }

    @Transactional
    public CouponId issue(CouponIssuedEvent event) {

        log.info("coupon issue event {}", event);
        CouponSearchCondition condition = new CouponSearchCondition(event.getMemberId(), event.getPromotionId());

        duplicateParticipationCheck(condition);

        Coupon coupon = couponRepository.findNotIssuedCoupon(condition)
                .orElseThrow(() -> {
                    log.info("member {} {}", condition.memberId(), CouponError.EMPTY_STOCK.getMessage());
                    throw new CouponException(CouponError.EMPTY_STOCK);
                });
        coupon.issuedCoupon(event.getMemberId());

        log.info("member {} issue coupon", condition.memberId());

        return coupon.getCouponId();
    }

    private void duplicateParticipationCheck(CouponSearchCondition condition) {
        CouponId couponWithMember = couponRepository.findAssignedCoupon(condition);
        if (couponWithMember != null) {
            log.info("member {} {}", condition.memberId(), CouponError.DUPLICATE_PARTICIPATION.getMessage());
            throw new CouponException(CouponError.DUPLICATE_PARTICIPATION);
        }
    }

    public List<CouponDto> myCoupons(String memberId) {
        return couponRepository.findMyCoupons(memberId);
    }

    public void usedCoupon(String couponId) {
        couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .useCoupon();
    }

    public void rollbackCoupon(String couponId) {
        couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .unUseCoupon();
    }

    public CouponDto findById(String couponId) {
        return CouponDto.convert(
                couponRepository.findById(CouponId.of(couponId)).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND)));
    }
}
