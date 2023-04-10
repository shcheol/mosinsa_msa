package com.shopping.mosinsa.controller;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.dto.CouponDto;
import com.shopping.mosinsa.dto.CouponEventDto;
import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    private final RedisTemplate<Long, Long> redisTemplate;

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

    @PatchMapping("/redis")
    public ResponseEntity<Void> couponIssuanceRequest(@RequestBody CouponIssuanceRequest request){
        ValueOperations<Long, Long> value = redisTemplate.opsForValue();
        Long customerId = request.getCustomerId();
        Long couponEventId = request.getCouponEventId();
        value.set(customerId,couponEventId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/redis/{key}")
    public ResponseEntity<?> getRedisKey(@PathVariable String key) {
        ValueOperations<Long, Long> vop = redisTemplate.opsForValue();
        Long value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }
}
