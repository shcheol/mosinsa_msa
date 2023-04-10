package com.shopping.mosinsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<Long, Long> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Long, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }
}
