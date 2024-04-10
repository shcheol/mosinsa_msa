package com.mosinsa.order.infra.feignclient;

import lombok.Getter;

@Getter
public class ExternalServerException extends RuntimeException {
    private final int status;
    private final String message;

    public ExternalServerException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
