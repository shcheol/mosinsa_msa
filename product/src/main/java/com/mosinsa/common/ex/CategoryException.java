package com.mosinsa.common.ex;

import lombok.Getter;

@Getter
public class CategoryException extends RuntimeException{

	private final CategoryError error;

	public CategoryException(CategoryError error) {
		super(error.getMessage());
		this.error = error;
	}

	public CategoryException(CategoryError error, String message) {
		super(message);
		this.error = error;
	}

	public CategoryException(CategoryError error, Throwable cause) {
		super(cause);
		this.error = error;
	}
}
