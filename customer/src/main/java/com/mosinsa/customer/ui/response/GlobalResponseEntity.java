package com.mosinsa.customer.ui.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponseEntity {

	public static ResponseEntity<BaseResponse> ok(Object data) {
		return new ResponseEntity<>(new GlobalResponse<>("success", data, null), HttpStatus.OK);
	}

	public static ResponseEntity<BaseResponse> success(HttpStatus httpStatus, Object data) {
		return new ResponseEntity<>(new GlobalResponse<>("success", data, null), httpStatus);
	}

	public static ResponseEntity<BaseResponse> error(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new GlobalResponse<>("error", null, message), httpStatus);
	}
}
