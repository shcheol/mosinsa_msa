package com.mosinsa.coupon.query.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import com.mosinsa.coupon.query.application.dto.CouponDto;
import com.mosinsa.promotion.domain.PromotionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponQueryServiceImpl implements CouponQueryService {
    private final CouponRepository couponRepository;

    @Override
    public CouponDto getCouponDetails(String couponId) {
        return CouponDto.convert(couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND)));
    }

    @Override
    public long count(String promotionId) {
        return couponRepository.countCouponsByPromotionIdAndMemberIdIsNull(PromotionId.of(promotionId));
    }

    @Override
    public List<CouponDto> getMyCoupons(String memberId) {
        return couponRepository.findMyCoupons(memberId);
    }

}
