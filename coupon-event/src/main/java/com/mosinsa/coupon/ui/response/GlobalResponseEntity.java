package com.mosinsa.coupon.ui.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalResponseEntity {

	public static ResponseEntity<BaseResponse> success(Object data) {
		return new ResponseEntity<>(new GlobalResponse<>("success", data, null), HttpStatus.OK);
	}

	public static ResponseEntity<BaseResponse> success(HttpStatus httpStatus, Object data) {
		return new ResponseEntity<>(new GlobalResponse<>("success", data, null), httpStatus);
	}

	public static ResponseEntity<BaseResponse> error(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new GlobalResponse<>("error", null, message), httpStatus);
	}
}
