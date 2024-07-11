package com.mosinsa.gateway.jwt;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class TestConfig{

	@Bean
	public Clock fixedClock(){
		return Clock.fixed(Instant.now(), ZoneId.systemDefault());
	}

}