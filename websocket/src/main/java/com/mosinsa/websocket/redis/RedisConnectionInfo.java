package com.mosinsa.websocket.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "redis")
public record RedisConnectionInfo(@DefaultValue("127.0.0.1") String host,
								  @DefaultValue("6379") int port) {

}
