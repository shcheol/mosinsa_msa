package com.mosinsa.customer.ui;

import com.mosinsa.customer.common.ex.CustomerException;
import com.mosinsa.customer.ui.response.BaseResponse;
import com.mosinsa.customer.ui.response.GlobalResponseEntity;
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
public class CustomerControllerAdvice {

	@ExceptionHandler(CustomerException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> productExceptionHandler(CustomerException exception) {
		log.error("response error message : {}", exception.getError().getMessage(), exception);
		return GlobalResponseEntity.error(exception.getError().getStatus(), exception.getError().getMessage());
	}


	@ExceptionHandler(Exception.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> exceptionHandler(Exception exception) {
		log.error("response error message : {}", exception.getMessage(), exception );
		return GlobalResponseEntity.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
