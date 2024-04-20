package com.mosinsa.product.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String key){
        log.info("lock {}", key);
        return redisTemplate.opsForValue()
                .setIfAbsent(key, "lock", Duration.ofMillis(3000));
    }

    public void unlock(String key){
        log.info("unlock {}", key);
        redisTemplate.delete(key);
    }
}
