package com.mosinsa.common.jwt;

import io.jsonwebtoken.Claims;

import java.time.Clock;

public interface Token {

	String create(String customerId, Clock clock);

	Claims getClaims(String token);

	boolean isValid(String token);
}
