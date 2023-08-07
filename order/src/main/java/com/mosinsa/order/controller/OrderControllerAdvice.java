package com.mosinsa.order.controller;

import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.controller.response.GlobalResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class OrderControllerAdvice {
	@ExceptionHandler(OrderException.class)
	@ResponseBody
	public static ResponseEntity<?> productExceptionHandler(OrderException exception) {
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
