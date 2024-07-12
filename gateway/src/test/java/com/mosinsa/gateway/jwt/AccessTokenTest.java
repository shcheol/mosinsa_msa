package com.mosinsa.gateway.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@Import({TestConfig.class})
class AccessTokenTest {

	@Value("${token.access.expiration}")
	private long tokenExpiration;


	@Autowired
	AccessToken accessToken;

	@Autowired
	Clock fixedClock;


	@Test
	void create() {

		String token = accessToken.create("customerId", fixedClock);

		Claims claims = accessToken.getClaims(token);
		assertThat(claims.getSubject()).isEqualTo("customerId");
		long expiration = fixedClock.millis() + tokenExpiration;
		long expected = new Date(expiration).getTime() / 1000 * 1000;

		assertThat(claims.getExpiration().getTime()).isBetween(expected-1000, expected+1000);

	}

	@Test
	void valid(){

		String token1 = accessToken.create("customerId",  Clock.fixed(Instant.now().minusMillis(tokenExpiration), ZoneId.systemDefault()));
		assertThat(accessToken.isValid(token1)).isFalse();


		String token2 = accessToken.create("customerId",  Clock.fixed(Instant.now().minusMillis(tokenExpiration+1000), ZoneId.systemDefault()));
		assertThat(accessToken.isValid(token2)).isFalse();

		String token3 = accessToken.create("customerId",  Clock.fixed(Instant.now().minusMillis(tokenExpiration-1000), ZoneId.systemDefault()));
		assertThat(accessToken.isValid(token3)).isTrue();
	}

}