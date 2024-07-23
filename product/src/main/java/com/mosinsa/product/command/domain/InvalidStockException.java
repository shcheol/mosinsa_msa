package com.mosinsa.product.command.domain;

public class InvalidStockException extends RuntimeException {

	public InvalidStockException() {
		super("수량부족");
	}
}
