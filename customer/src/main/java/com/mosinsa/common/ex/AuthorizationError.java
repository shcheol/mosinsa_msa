package com.mosinsa.common.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthorizationError {

	JWT_VALID_ERROR("jwt token is not valid", HttpStatus.UNAUTHORIZED),
	EMPTY_AUTHORIZATION_TOKEN("no authorization header", HttpStatus.UNAUTHORIZED),

	;


	private final String message;
	private final HttpStatus status;

	AuthorizationError(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}
}
