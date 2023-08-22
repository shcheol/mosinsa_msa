package com.mosinsa.couponissue.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mosinsa.couponissue.controller.request.CouponEventCreateRequest;
import com.mosinsa.couponissue.controller.request.CouponIssuanceRequest;
import com.mosinsa.couponissue.dto.CouponDto;
import com.mosinsa.couponissue.dto.CouponEventDto;
import com.mosinsa.couponissue.entity.Coupon;
import com.mosinsa.couponissue.entity.CouponEvent;
import com.mosinsa.couponissue.service.CouponService;
import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.service.CouponQueueService;
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

	private final CouponQueueService couponQueueService;


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

    @PostMapping("/enqueue")
    public ResponseEntity<String> couponIssuanceRequest(@RequestBody CouponIssuanceRequest request) throws JsonProcessingException {

		if (couponQueueService.enqueue(request)){
			Long rank = couponQueueService.lookupOrder(request);
			return ResponseEntity.ok().body(String.format("success: %s번째 순서입니다.", rank));
		} else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
		}
    }

    @GetMapping("/enqueue")
    public ResponseEntity<String> lookupOrder(@RequestBody CouponIssuanceRequest request) throws JsonProcessingException {

		Long rank = couponQueueService.lookupOrder(request);

        return new ResponseEntity<>(String.format("'%s' 님 앞에 %s명 남았습니다.",request.getCustomerId(), rank), HttpStatus.OK);
    }
}
