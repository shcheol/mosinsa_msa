package com.mosinsa.customer.common.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomerError {

	INTERNAL_SERVER_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
	REQ_API_SERVER_FAIL("API서버 요청중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),
	CUSTOMER_NOT_FOUND("고객 정보가 없습니다.", HttpStatus.NOT_FOUND),
	VALIDATION_ERROR("값 검증 에러", HttpStatus.INTERNAL_SERVER_ERROR),
	WRONG_ID_OR_PASSWORD("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
	DUPLICATED_LOGINID("이미 존재하는 ID입니다.", HttpStatus.BAD_REQUEST)

	;


	private String message;
	private HttpStatus status;

	CustomerError(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}
}
