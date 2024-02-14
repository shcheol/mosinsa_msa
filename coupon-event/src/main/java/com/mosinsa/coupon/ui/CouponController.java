package com.mosinsa.coupon.ui;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.coupon.domain.CouponIssuedEvent;
import com.mosinsa.coupon.dto.CouponDto;
import com.mosinsa.coupon.ui.request.CreateCouponRequest;
import com.mosinsa.coupon.ui.response.CouponResponse;
import com.mosinsa.promotion.dto.JoinPromotionRequest;
import com.mosinsa.promotion.dto.JoinResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

	@PatchMapping("/coupons/{couponId}")
	public ResponseEntity<JoinResult> useCoupon(@PathVariable("couponId") String couponId) {

		couponService.usedCoupon(couponId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/coupons/my/{memberId}")
	public ResponseEntity<List<CouponDto>> myCoupons(@PathVariable("memberId") String memberId) {

		List<CouponDto> couponDtos = couponService.myCoupons(memberId);

		return ResponseEntity.ok(couponDtos);
	}

    @PatchMapping("/coupons")
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
    public ResponseEntity<JoinResult> createCouponForNewMember(@RequestBody CreateCouponRequest request) {

        couponService.createForNewMember(request.memberId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
