package com.mosinsa.product.command.domain;

public class InvalidStockException extends RuntimeException {

	public InvalidStockException(String message) {
		super(message);
	}
}
