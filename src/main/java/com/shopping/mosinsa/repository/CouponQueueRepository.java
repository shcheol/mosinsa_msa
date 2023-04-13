package com.shopping.mosinsa.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class CouponQueueRepository {

	private final ZSetOperations<String, String> sortedSet;

	public CouponQueueRepository(RedisTemplate<String, String> redisTemplate) {
		this.sortedSet = redisTemplate.opsForZSet();
	}

	public boolean enqueue(String key, String value, Double score){
		return Boolean.TRUE.equals(sortedSet.addIfAbsent(key, value, score));
	}

	public Long rank(String key, String value){
		return sortedSet.rank(key, value);
	}

	public Set<String> range(String key, long start, long end){
		return sortedSet.range(key, start, end);
	}

	public void dequeue(String key, long count){
		sortedSet.popMin(key, count);
	}

}
