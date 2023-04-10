package com.shopping.mosinsa.controller;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.dto.CouponDto;
import com.shopping.mosinsa.dto.CouponEventDto;
import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;


    @GetMapping("/event/{id}")
    public ResponseEntity<CouponEventDto> createEvent(@PathVariable Long id){

        CouponEvent couponEvent = couponService.findCouponEvent(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CouponEventDto(couponEvent));
    }

    @PostMapping("/event")
    public ResponseEntity<CouponEventDto> createEvent(@RequestBody CouponEventCreateRequest request){

        CouponEvent couponEvent = couponService.createCouponEvent(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CouponEventDto(couponEvent));
    }

    @PatchMapping
    public ResponseEntity<CouponDto> issuanceRequest(@RequestBody CouponIssuanceRequest request){

        Coupon coupon = couponService.couponIssuanceRequest(request);

        return ResponseEntity.status(HttpStatus.OK).body(new CouponDto(coupon));
    }

}
