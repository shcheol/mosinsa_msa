package com.mosinsa.order.infra.feignclient;

import lombok.Getter;

@Getter
public class ExternalErrorException extends RuntimeException {
    private final ResponseResult result;

    public ExternalErrorException(ResponseResult result) {
        super(result.getMessage());
        this.result = result;
    }

    public ExternalErrorException(String message, ResponseResult result) {
        super(result.getMessage());
        this.result = result;
    }




}
