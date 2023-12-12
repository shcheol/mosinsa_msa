package com.mosinsa.order.common.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OrderError {

	INTERNAL_SERVER_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_ENOUGH_PRODUCT_STOCK("상품 수량이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	REQ_API_SERVER_FAIL("API서버 요청중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),
	ORDER_NOT_FOUND("주문 정보가 없습니다.", HttpStatus.NOT_FOUND),
	VALIDATION_ERROR("값 검증 에러", HttpStatus.INTERNAL_SERVER_ERROR),

	ORDER_AT_LEAST_ONE_OR_MORE_PRODUCTS("상품을 한개 이상 주문해야합니다.", HttpStatus.BAD_REQUEST),
	NO_ORDER_CUSTOMER("주문자가 없습니다.", HttpStatus.BAD_REQUEST);

	private String message;
	private HttpStatus status;
	OrderError(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}


}
