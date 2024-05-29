package com.mosinsa.order.command.application;

public class NotEnoughProductStockException extends RuntimeException {

	public NotEnoughProductStockException(String message) {
		super(message);
	}
}
