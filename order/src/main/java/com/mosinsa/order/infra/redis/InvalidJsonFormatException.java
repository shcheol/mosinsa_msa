package com.mosinsa.order.infra.redis;

public class InvalidJsonFormatException extends RuntimeException {
    public InvalidJsonFormatException(Throwable cause) {
        super(cause);
    }
}
