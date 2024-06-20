package com.mosinsa.review.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BroadcastMessageHandler {

	private final RedisTemplate<String, String> redisTemplate;

	public void publish(String productId, Object content){
		log.info("broadcast review likes event {}", content);
		redisTemplate.convertAndSend(productId, content);

	}
}
