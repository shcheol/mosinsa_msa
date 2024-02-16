package com.mosinsa.order.common.ex;

public class OrderRollbackException extends RuntimeException{
	public OrderRollbackException() {
		super();
	}

	public OrderRollbackException(String message) {
		super(message);
	}

	public OrderRollbackException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderRollbackException(Throwable cause) {
		super(cause);
	}

	protected OrderRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
