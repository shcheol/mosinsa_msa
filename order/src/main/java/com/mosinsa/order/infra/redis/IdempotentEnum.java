package com.mosinsa.order.infra.redis;

public enum IdempotentEnum {
    ORDER_IDEMPOTENT_KEY("order_idempotent_key");

    IdempotentEnum(String headerKey) {
    }
}
