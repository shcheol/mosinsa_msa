package com.mosinsa.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CouponError {

    INVALID_PERIOD_INPUT("유효하지 않은 기간입니다.", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY("유효하지 않은 수량입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EMPTY_STOCK("남은 수량이 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_PARTICIPATION("중복 참여입니다.", HttpStatus.NOT_FOUND),
    ;

    private String message;

    private HttpStatus status;

    CouponError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
