package com.mosinsa.order.infra.api;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ExternalServerException extends RuntimeException {
	private final HttpStatusCode status;
	private final String message;

	public ExternalServerException(HttpStatusCode status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}

}
