package com.mosinsa.product.ui;

import com.mosinsa.product.ui.response.BaseResponse;
import com.mosinsa.product.ui.response.GlobalResponseEntity;
import com.mosinsa.common.ex.ProductException;
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
public class ProductControllerAdvice {

	@ExceptionHandler(ProductException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> productExceptionHandler(ProductException exception) {
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
