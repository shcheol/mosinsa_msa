package com.mosinsa.coupon.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;
import com.mosinsa.promotion.application.dto.JoinPromotionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons/{couponId}")
    public ResponseEntity<Void> useCoupon(@PathVariable("couponId") String couponId) {

        couponService.useCoupon(couponId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/coupons/{couponId}/cancel")
    public ResponseEntity<Void> cancelCoupon(@PathVariable("couponId") String couponId) {

        couponService.rollbackCoupon(couponId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/coupons/issue")
    public ResponseEntity<Void> joinPromotion(@Login CustomerInfo customerInfo, @RequestBody JoinPromotionRequest request) {

        CouponIssuedEvent couponIssuedEvent = new CouponIssuedEvent(customerInfo.id(), request.promotionId());
        couponService.issue(couponIssuedEvent);

        return ResponseEntity.ok().build();
    }

}
