package com.mosinsa.common.ex;

import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException{

	private final ReviewError error;

	public ReviewException(ReviewError error) {
		super(error.getMessage());
		this.error = error;
	}

	public ReviewException(ReviewError error, String message) {
		super(message);
		this.error = error;
	}

	public ReviewException(ReviewError error, Throwable cause) {
		super(cause);
		this.error = error;
	}
}
