package com.shopping.mosinsa.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.dto.CouponDto;
import com.shopping.mosinsa.dto.CouponEventDto;
import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.service.CouponService;
import io.restassured.internal.mapping.Jackson1Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


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

    @PostMapping("/redis")
    public ResponseEntity<String> couponIssuanceRequest(@RequestBody CouponIssuanceRequest request) throws JsonProcessingException {
        Long customerId = request.getCustomerId();
        String couponEventName = request.getEventName();
        Long couponEventId = request.getCouponEventId();
        ZSetOperations<String, String> sortedSet = redisTemplate.opsForZSet();
        Timestamp timestamp = new Timestamp(System.nanoTime());

        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(request);

        Boolean aBoolean = sortedSet.addIfAbsent(String.format("%s%s", couponEventName, couponEventId), s, Long.valueOf(timestamp.getTime()).doubleValue());

        if(Boolean.TRUE.equals(aBoolean))
            return ResponseEntity.ok().body("success");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
    }

    @GetMapping("/redis/{key}")
    public ResponseEntity<?> getRedisKey(@PathVariable String key) {
        ZSetOperations<String, String> sortedSet = redisTemplate.opsForZSet();
        ZSetOperations.TypedTuple<String> stringTypedTuple = sortedSet.popMax(key);
        assert stringTypedTuple != null;
        String value = stringTypedTuple.getValue();
        return new ResponseEntity<>(value, HttpStatus.OK);
    }
}
