package com.mosinsa.websocket.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "redis")
public class RedisConnectionInfo {

		private String host ="127.0.0.1";
		private int port = 6379;
}
