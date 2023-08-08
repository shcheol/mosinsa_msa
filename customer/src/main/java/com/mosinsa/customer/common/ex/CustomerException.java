package com.mosinsa.customer.common.ex;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException{

	private CustomerError error;

	public CustomerException(CustomerError error) {
		super(error.getMessage());
		this.error = error;
	}

	public CustomerException(CustomerError error, String message) {
		super(message);
		this.error = error;
	}

	public CustomerException(CustomerError error, Throwable cause) {
		super(error.getMessage(), cause);
		this.error = error;
	}
}
