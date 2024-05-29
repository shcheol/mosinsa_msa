package com.mosinsa.order.command.domain;

public class AlreadyCanceledException extends RuntimeException {
    public AlreadyCanceledException(String message) {
        super(message);
    }
}
