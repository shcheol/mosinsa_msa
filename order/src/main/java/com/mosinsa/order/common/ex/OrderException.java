package com.mosinsa.order.common.ex;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException{

	private OrderError error;

	public OrderException(OrderError error) {
		super(error.getMessage());
		this.error = error;
	}

	public OrderException(OrderError error, String message) {
		super(message);
		this.error = error;
	}

	public OrderException(OrderError error, Throwable cause) {
		super(error.getMessage(), cause);
		this.error = error;
	}
}
