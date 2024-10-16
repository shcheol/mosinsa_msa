package com.mosinsa.websocket.redis;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties(RedisConnectionInfo.class)
public class RedisConfig {

	private final RedisConnectionInfo redisInfo;

	public RedisConfig(RedisConnectionInfo redisInfo) {
		this.redisInfo = redisInfo;
	}

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(redisInfo.getHost(), redisInfo.getPort());
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {

		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}
}
