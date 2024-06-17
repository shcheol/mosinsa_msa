package com.mosinsa.common.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewError {

	INTERNAL_SERVER_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_FOUNT_REVIEW("리뷰를 찾을수 없습니다.", HttpStatus.NOT_FOUND),
	NOT_FOUNT_COMMENT("댓글을 찾을수 없습니다.", HttpStatus.NOT_FOUND),

	VALIDATION_ERROR("값 검증 에러", HttpStatus.INTERNAL_SERVER_ERROR),

	;


	private String message;
	private HttpStatus status;

	ReviewError(String message, HttpStatus status){
		this.message = message;
		this.status = status;
	}

}
