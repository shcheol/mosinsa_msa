package com.mosinsa.common.ex;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException{

	private final ProductError error;

	public ProductException(ProductError error) {
		super(error.getMessage());
		this.error = error;
	}

	public ProductException(ProductError error, String message) {
		super(message);
		this.error = error;
	}

	public ProductException(ProductError error, Throwable cause) {
		super(cause);
		this.error = error;
	}
}
