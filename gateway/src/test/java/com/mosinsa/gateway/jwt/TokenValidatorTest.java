package com.mosinsa.gateway.jwt;

import com.mosinsa.gateway.filter.AuthorizationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@Import({TestConfig.class})
class TokenValidatorTest {

	@Autowired
	TokenProvider provider;

	@Autowired
	TokenValidator validator;

	@Test
	void validateAndGet(){

		TokenVo tokens = provider.create("customerId1");

		TokenVo tokenVo = validator.validateAndGet(tokens);

		assertThat(tokenVo.accessToken()).isEqualTo(tokens.accessToken());
		assertThat(tokenVo.refreshToken()).isEqualTo(tokens.refreshToken());

	}

	@Test
	void validateAndGetFailWhenEmptyToken(){

		TokenVo tokenVo = new TokenVo("", "1234");

		assertThrows(AuthorizationException.class, () -> validator.validateAndGet(tokenVo));
	}

}