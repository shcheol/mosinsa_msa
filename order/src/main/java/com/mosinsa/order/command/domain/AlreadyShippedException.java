package com.mosinsa.order.command.domain;

public class AlreadyShippedException extends RuntimeException {
	public AlreadyShippedException(String message) {
		super(message);
	}
}
