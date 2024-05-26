package com.mosinsa.order.infra.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcs.idempotencyapi.repository.IdempotencyKeyStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisIdempotentRepository implements IdempotencyKeyStore {

    private final ValueOperations<String, String> redisStore;

    public RedisIdempotentRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisStore = redisTemplate.opsForValue();
    }

    @Override
    public boolean has(String key) {
        return StringUtils.hasText(redisStore.get(key));
    }

    @Override
    public void set(String key, Object value) {
        ObjectMapper om = new ObjectMapper();
        try {
            String s = om.writeValueAsString(value);
            redisStore.setIfAbsent(key, s, 300, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonFormatException(e);
        }
    }

    @Override
    public Object get(String key) {
        return redisStore.get(key);
    }

    @Override
    public void remove(String key) {
        redisStore.getAndDelete(key);
    }
}
