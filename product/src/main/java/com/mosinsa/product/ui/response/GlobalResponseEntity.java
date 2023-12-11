package com.mosinsa.product.ui.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalResponseEntity {

	public static ResponseEntity<?> Ok(Object data){
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public static ResponseEntity<?> Ok(HttpStatus httpStatus, Object data){
		return new ResponseEntity<>(new GlobalResponse<>("success", data, null), httpStatus);
	}

	public static ResponseEntity<?> Error(HttpStatus httpStatus, String message){
		return new ResponseEntity<>(new GlobalResponse<>("error", null, message), httpStatus);
	}
}
