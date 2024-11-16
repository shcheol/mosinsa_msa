package com.mosinsa.coupon.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.coupon.query.application.CouponQueryService;
import com.mosinsa.coupon.query.application.dto.CouponDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ViewCouponController {

    private final CouponQueryService couponQueryService;

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponDetails> details(@PathVariable("couponId") String couponId) {

        CouponDetails findCoupon = couponQueryService.getCouponDetails(couponId);
        return ResponseEntity.ok(findCoupon);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<CouponDetails>> myCoupons(@Login CustomerInfo customerInfo) {

        List<CouponDetails> couponDetails = couponQueryService.getMyCoupons(customerInfo.id());

        return ResponseEntity.ok(couponDetails);
    }
}
