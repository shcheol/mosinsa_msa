package com.mosinsa.coupon.ui;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.coupon.domain.CouponIssuedEvent;
import com.mosinsa.coupon.dto.CouponDto;
import com.mosinsa.coupon.ui.request.CreateCouponRequest;
import com.mosinsa.promotion.dto.JoinPromotionRequest;
import com.mosinsa.promotion.dto.JoinResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupons/{couponId}")
    public String details(@PathVariable("couponId") String couponId, Model model) {

        model.addAttribute("coupon", couponService.findById(couponId));

        return "my/couponDetail";
    }

    @GetMapping("/coupons/my/{memberId}")
    public String myCoupons(@PathVariable("memberId") String memberId, Model model) {

        List<CouponDto> couponDtos = couponService.myCoupons(memberId);
        model.addAttribute("coupons", couponDtos);

        return "my/coupons";
    }

    @PatchMapping("/coupons")
    @ResponseBody
    public ResponseEntity<JoinResult> joinPromotion(@RequestBody JoinPromotionRequest request) {
        String promotionId = request.promotionId();
        String memberId = request.memberId();
        log.info("promotionId: {}, memberId: {}", promotionId, memberId);
        if (!StringUtils.hasText(memberId)) {
            return ResponseEntity.badRequest().body(new JoinResult("login first"));
        }
        CouponIssuedEvent couponIssuedEvent = new CouponIssuedEvent(memberId, promotionId);
        try {
            couponService.issue(couponIssuedEvent);
        } catch (CouponException e) {
            return ResponseEntity.badRequest().body(new JoinResult(e.getMessage()));
        }
        return ResponseEntity.ok(new JoinResult("발급되었습니다."));
    }

    @PostMapping("/coupons")
    @ResponseBody
    public ResponseEntity<JoinResult> createCouponForNewMember(@RequestBody CreateCouponRequest request) {

        couponService.createForNewMember(request.memberId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
