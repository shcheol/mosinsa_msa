package com.mosinsa.product.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockOperation {

	private RedisTemplate<String, String> redisTemplate;

//	public



}
