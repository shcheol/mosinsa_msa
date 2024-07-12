package com.mosinsa.customer.common.ex;

public class AuthorizationException extends RuntimeException {

	private final AuthorizationError error;

	public AuthorizationException(AuthorizationError error) {
		this.error = error;
	}

	public AuthorizationException(String message, Throwable cause, AuthorizationError error) {
		super(message, cause);
		this.error = error;
	}

	public AuthorizationError getError() {
		return error;
	}
}
