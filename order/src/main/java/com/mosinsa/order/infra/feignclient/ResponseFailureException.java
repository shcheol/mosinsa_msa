package com.mosinsa.order.infra.feignclient;

import feign.Response;
import lombok.Getter;

@Getter
public class ResponseFailureException extends RuntimeException {

    private final Response response;

    public ResponseFailureException(Response response) {
        super();
        this.response = response;
    }

    public ResponseFailureException(String message, Response response) {
        super(message);
        this.response = response;
    }
}
