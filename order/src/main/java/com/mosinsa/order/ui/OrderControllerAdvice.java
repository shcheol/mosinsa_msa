package com.mosinsa.order.ui;

import com.mosinsa.common.ex.OrderException;
import com.mosinsa.order.infra.api.ExternalServerException;
import com.mosinsa.order.ui.response.BaseResponse;
import com.mosinsa.order.ui.response.GlobalResponseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderControllerAdvice {

	@ExceptionHandler(OrderException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> orderExceptionHandler(OrderException exception) {
		log.error("order exception occurred : {}", exception.getError().getMessage(), exception);
		return GlobalResponseEntity.error(exception.getError().getStatus(), exception.getError().getMessage());
	}

	@ExceptionHandler(ExternalServerException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> externalExceptionHandler(ExternalServerException exception) {
		log.error("external server error occurred : {}", exception.getMessage(), exception);
		return GlobalResponseEntity.error(HttpStatus.valueOf(exception.getStatus().value()), exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> exceptionHandler(Exception exception) {
		log.error("generic error occurred : {}", exception.getMessage(), exception);
		return GlobalResponseEntity.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
