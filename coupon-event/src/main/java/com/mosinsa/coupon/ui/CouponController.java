package com.mosinsa.coupon.ui;

import com.mosinsa.coupon.command.application.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

        couponService.cancelCoupon(couponId);
        return ResponseEntity.ok().build();
    }
}
