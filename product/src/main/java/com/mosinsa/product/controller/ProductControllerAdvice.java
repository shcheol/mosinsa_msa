package com.mosinsa.product.controller;

import com.mosinsa.product.controller.response.GlobalResponseEntity;
import com.mosinsa.product.common.ex.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ProductControllerAdvice {

	@ExceptionHandler(ProductException.class)
	@ResponseBody
	public static ResponseEntity<?> productExceptionHandler(ProductException exception) {
		log.error("response error message : {}", exception.getError().getMessage(), exception);
		return GlobalResponseEntity.Error(exception.getError().getStatus(), exception.getError().getMessage());
	}


	@ExceptionHandler(Exception.class)
	@ResponseBody
	public static ResponseEntity<?> exceptionHandler(Exception exception) {
		log.error("response error message : {}", exception.getMessage(), exception );
		return GlobalResponseEntity.Error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
