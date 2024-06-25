package com.mosinsa.websocket.kafka;

public record ReviewLikesEvent(String productId, String reviewId, boolean canceled) {
}
