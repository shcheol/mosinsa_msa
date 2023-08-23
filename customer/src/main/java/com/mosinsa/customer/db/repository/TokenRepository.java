package com.mosinsa.customer.db.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {

	private final ValueOperations<String, String> map;

	public TokenRepository(RedisTemplate<String, String> redisTemplate) {
		this.map = redisTemplate.opsForValue();
	}

	public void put(String key, String value, Long timeout){
		map.set(key, value, timeout, TimeUnit.MICROSECONDS);
	}

	public String get(String key){
		return map.get(key);
	}

	public void remove(String key){
		map.getAndDelete(key);
	}
}
