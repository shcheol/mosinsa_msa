package com.mosinsa.websocket.kafka;

public record ReviewDislikesEvent(String productId, String reviewId, boolean canceled) {
}
