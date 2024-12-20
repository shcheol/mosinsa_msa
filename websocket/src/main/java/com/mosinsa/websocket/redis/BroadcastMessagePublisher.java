package com.mosinsa.websocket.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BroadcastMessagePublisher {

	private final RedisTemplate<String, String> redisTemplate;
	private final MessageSubscriber redisSubscriber;
	private final RedisMessageListenerContainer redisMessageListenerContainer;

	public void publish(String channel, Object content) {

		redisMessageListenerContainer.addMessageListener(redisSubscriber, new ChannelTopic(channel));

		log.info("broadcast event {}", content);
		redisTemplate.convertAndSend(channel, content);
	}
}
