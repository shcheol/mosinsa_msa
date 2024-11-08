package com.mosinsa.promotion.infra.api;

public record ErrorResponse(String message, String code, int status) {
}
