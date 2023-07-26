package com.mosinsa.product.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class IdempotentComponent {

	private final HashOperations<String, String, String> map;
	private final String key;

	private final RedisTemplate<String, String> redisTemplate;

	public IdempotentComponent(RedisTemplate<String, String> redisTemplate){
		this.redisTemplate = redisTemplate;
		map = this.redisTemplate.opsForHash();
		key = this.getClass().getSimpleName();
	}

	public String get(String idempotent){
		log.info("get key:{}, idempotent key: {}", key, idempotent);
		return map.get(key, idempotent);
	}

	public void put(String idempotent, String content){
		log.info("put key:{}, idempotent key: {}", key, idempotent);
		map.put(key, idempotent, content);
		redisTemplate.expire(key, 30, TimeUnit.SECONDS);
	}
}
