package com.mosinsa.order.infra.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisIdempotentRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public <T> String setData(T data){

        ObjectMapper om = new ObjectMapper();
        try {
            String s = om.writeValueAsString(data);
            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
            String key = UUID.randomUUID().toString();
            Boolean aBoolean = stringStringValueOperations.setIfAbsent(key, s, 300, TimeUnit.SECONDS);
            if (Boolean.TRUE.equals(aBoolean)){
                return key;
            }
            throw new RuntimeException();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void verifyIdempotent(String key, T data){

        if (key == null || key.isEmpty()){
            // 멱등성 키 없는 주문
            throw new OrderException(OrderError.NO_IDEMPOTENT_KEY);
        }

        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String originData = stringStringValueOperations.getAndDelete(key);

        if (originData == null || originData.isEmpty()){
            // key 매칭 실패
            // 타임아웃, 변조 키
            throw new OrderException(OrderError.INVALID_IDEMPOTENT_KEY);
        }

        ObjectMapper om = new ObjectMapper();
        try {
            String s = om.writeValueAsString(data);
            log.info("origin {}", originData);
            log.info("new {}", s);
            if (!originData.equals(s)){
                // 기존 요청과 내용이 달라짐, 데이터 변조
                throw new OrderException(OrderError.INVALID_DATA_IDEMPOTENT_KEY);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
