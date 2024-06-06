package com.mosinsa.common.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductError {

	INTERNAL_SERVER_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_ENOUGH_PRODUCT_STOCK("상품 수량이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_FOUNT_PRODUCT("상품을 찾을수 없습니다.", HttpStatus.NOT_FOUND),

	VALIDATION_ERROR("값 검증 에러", HttpStatus.INTERNAL_SERVER_ERROR),

	;


	private String message;
	private HttpStatus status;

	ProductError(String message, HttpStatus status){
		this.message = message;
		this.status = status;
	}

}
