package com.mosinsa.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class GatewayConfig {

	@Bean
	public Clock clock(){
		return Clock.systemDefaultZone();
	}
}
