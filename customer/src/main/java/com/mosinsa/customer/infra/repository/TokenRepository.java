package com.mosinsa.customer.infra.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public void put(String key, String value, Long timeout){
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
	}

	public String get(String key){
		return redisTemplate.opsForValue().get(key);
	}

	public boolean remove(String key){
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

}
