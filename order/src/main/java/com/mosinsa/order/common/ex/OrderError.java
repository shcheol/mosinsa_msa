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
	NO_IDEMPOTENT_KEY("멱등키 없는 API 요청", HttpStatus.BAD_REQUEST),
	INVALID_IDEMPOTENT_KEY("매칭되는 멱등키가 없습니다.", HttpStatus.NOT_FOUND),
	INVALID_DATA_IDEMPOTENT_KEY("주문서와 요청이 다릅니다.", HttpStatus.CONFLICT),
	ORDER_AT_LEAST_ONE_OR_MORE_PRODUCTS("상품을 한개 이상 주문해야합니다.", HttpStatus.BAD_REQUEST),
	NO_ORDER_CUSTOMER("주문자가 없습니다.", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED_ERROR("인증 실패", HttpStatus.UNAUTHORIZED),
	;

	private String message;
	private HttpStatus status;

	OrderError(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}


}
