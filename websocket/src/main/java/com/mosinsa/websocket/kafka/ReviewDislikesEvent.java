package com.mosinsa.websocket.kafka;

public record ReviewDislikesEvent(String reviewId, boolean canceled) {
}
