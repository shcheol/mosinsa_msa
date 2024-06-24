package com.mosinsa.websocket.kafka;

public record ReviewLikesEvent(String reviewId, boolean canceled) {
}
