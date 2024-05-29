package com.mosinsa.product.domain.product;

public class InvalidStockException extends RuntimeException {

	public InvalidStockException(String message) {
		super(message);
	}
}
