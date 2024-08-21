package com.mosinsa.coupon.query.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.infra.jpa.CouponRepository;
import com.mosinsa.coupon.query.application.dto.CouponDetails;
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
    public CouponDetails getCouponDetails(String couponId) {
        return CouponDetails.convert(couponRepository.findById(CouponId.of(couponId))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND)));
    }

    @Override
    public List<CouponDetails> getMyCoupons(String memberId) {
        return couponRepository.findMyCoupons(memberId).stream()
                .map(CouponDetails::convert).toList();
    }

}
