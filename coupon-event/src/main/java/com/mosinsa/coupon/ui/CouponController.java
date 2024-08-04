package com.mosinsa.coupon.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.coupon.application.dto.CouponDto;
import com.mosinsa.coupon.domain.CouponIssuedEvent;
import com.mosinsa.coupon.ui.response.CouponResponse;
import com.mosinsa.promotion.application.dto.JoinPromotionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResponse> details(@PathVariable("couponId") String couponId) {

        CouponDto findCoupon = couponService.findById(couponId);
        return ResponseEntity.ok(new CouponResponse(findCoupon));
    }

    @PostMapping("/coupons/{couponId}")
    public ResponseEntity<Void> useCoupon(@PathVariable("couponId") String couponId) {

        couponService.usedCoupon(couponId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/coupons/{couponId}/cancel")
    public ResponseEntity<Void> cancelCoupon(@PathVariable("couponId") String couponId) {

        couponService.rollbackCoupon(couponId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/coupons/my/{memberId}")
    public ResponseEntity<List<CouponDto>> myCoupons(@PathVariable("memberId") String memberId) {

        List<CouponDto> couponDtos = couponService.myCoupons(memberId);

        return ResponseEntity.ok(couponDtos);
    }

    @PostMapping("/coupons/issue")
    public ResponseEntity<Void> joinPromotion(@Login CustomerInfo customerInfo, @RequestBody JoinPromotionRequest request) {

        CouponIssuedEvent couponIssuedEvent = new CouponIssuedEvent(customerInfo.id(), request.promotionId());
        couponService.issue(couponIssuedEvent);

        return ResponseEntity.ok().build();
    }

}
