package com.mosinsa.common.exception;

import com.mosinsa.coupon.ui.response.BaseResponse;
import com.mosinsa.coupon.ui.response.GlobalResponseEntity;
import com.mosinsa.promotion.infra.api.ExternalServerException;
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
public class GlobalControllerAdvice {

	@ExceptionHandler(CouponException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> couponExceptionHandler(CouponException exception) {
		log.error("CouponException : {}", exception.getError().getMessage());
		return GlobalResponseEntity.error(exception.getError().getStatus(), exception.getError().getMessage());
	}

	@ExceptionHandler(ExternalServerException.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> couponExceptionHandler(ExternalServerException exception) {
		log.error("ExternalServerException : {}", exception.getMessage());
		return GlobalResponseEntity.error(HttpStatus.valueOf(exception.getStatus().value()), exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public static ResponseEntity<BaseResponse> exceptionHandler(Exception exception) {
		log.error("Exception : {}", exception.getMessage(), exception);
		return GlobalResponseEntity.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
