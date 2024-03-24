package com.mosinsa.order.ui;

import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.infra.feignclient.ExternalErrorException;
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
		log.error("response error message : {}", exception.getError().getMessage(), exception);
		return GlobalResponseEntity.error(exception.getError().getStatus(), exception.getError().getMessage());
	}

	@ExceptionHandler(ExternalErrorException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> ExternalExceptionHandler(ExternalErrorException exception) {
		log.error("response error message : {}", exception.getMessage(), exception);
		return GlobalResponseEntity.error(HttpStatus.valueOf(exception.getResult().getStatus()), exception.getResult().getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> exceptionHandler(Exception exception) {
		log.error("response error message : {}", exception.getMessage(), exception);
		return GlobalResponseEntity.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
