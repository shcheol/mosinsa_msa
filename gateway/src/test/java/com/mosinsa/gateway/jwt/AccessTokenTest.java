package com.mosinsa.gateway.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({TestConfig.class})
class AccessTokenTest {

	@Autowired
	AccessToken accessToken;


	@Test
	void create(){
		Clock fixed = Clock.fixed(Instant.now(), ZoneId.systemDefault());

//		AccessToken accessToken = new AccessToken(fixed);

		String token = accessToken.create("customerId");
		System.out.println(token);

		Date date = new Date(Clock.systemDefaultZone().millis());
		Date date2 = new Date(System.currentTimeMillis());
		Date date3 = new Date();

		System.out.println("date = " + date);
		System.out.println("date2 = " + date2);
		System.out.println("date3 = " + date3);

	}

	@Test
	void valid(){

		String token = accessToken.create("customerId",  Clock.fixed(Instant.now().minusMillis(360000-1), ZoneId.systemDefault()));
		System.out.println(token);

		boolean valid = accessToken.isValid(token);
		System.out.println(valid);

	}

}