package com.mosinsa.order.command.application;

public class NotEnoughProductStockException extends RuntimeException {

	public NotEnoughProductStockException() {
		super("상품수량이 부족합니다.");
	}
}
