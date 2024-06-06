package com.mosinsa.common.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CategoryError {

	INTERNAL_SERVER_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_FOUNT_CATEGORY("존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND),
	VALIDATION_ERROR("값 검증 에러", HttpStatus.INTERNAL_SERVER_ERROR),

	;


	private String message;
	private HttpStatus status;

	CategoryError(String message, HttpStatus status){
		this.message = message;
		this.status = status;
	}

}
