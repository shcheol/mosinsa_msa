package com.mosinsa.order.infra.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
            Boolean aBoolean = stringStringValueOperations.setIfAbsent(key, s);
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
            throw new RuntimeException();
        }

        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String originData = stringStringValueOperations.get(key);

        if (originData == null || originData.isEmpty()){
            // key 매칭 실패
            // 타임아웃, 변조 키
            throw new RuntimeException();
        }

        ObjectMapper om = new ObjectMapper();
        try {
            String s = om.writeValueAsString(data);
            if (!originData.equals(s)){
                // 기존 요청과 내용이 달라짐, 데이터 변조
                throw new RuntimeException();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

    }

}
