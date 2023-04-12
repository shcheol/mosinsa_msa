package com.shopping.mosinsa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.dto.CouponDto;
import com.shopping.mosinsa.dto.CouponEventDto;
import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    private final RedisTemplate<String, String> redisTemplate;

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
        String eventName = request.getEventName();
        Long couponEventId = request.getCouponEventId();
        ZSetOperations<String, String> sortedSet = redisTemplate.opsForZSet();
        Timestamp timestamp = new Timestamp(System.nanoTime());

        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(request);
		System.out.println("s = " + s);

        Boolean aBoolean = sortedSet.addIfAbsent(String.format("%s%s", eventName, couponEventId), s, Long.valueOf(timestamp.getTime()).doubleValue());

        if(Boolean.TRUE.equals(aBoolean))
            return ResponseEntity.ok().body("success");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
    }

    @GetMapping("/enqueue")
    public ResponseEntity<String> lookupOrder(@RequestBody CouponIssuanceRequest request) throws JsonProcessingException {

		String eventName = request.getEventName();
		Long couponEventId = request.getCouponEventId();
		String key = String.format("%s%s", eventName, couponEventId);

		Long size = redisTemplate.opsForZSet().size(key);
		System.out.println("size = " + size);
		ObjectMapper om = new ObjectMapper();
		String s = om.writeValueAsString(request);
		System.out.println("s = " + s);

		Long rank = redisTemplate.opsForZSet().rank(key, s);
		System.out.println("rank = " + rank);

        return new ResponseEntity<>(String.format("'%s' 님 앞에 %s명 남았습니다.",request.getCustomerId(), rank), HttpStatus.OK);
    }
}
