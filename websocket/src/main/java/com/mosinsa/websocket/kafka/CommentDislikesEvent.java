package com.mosinsa.websocket.kafka;

public record CommentDislikesEvent(String productId, String commentId, boolean canceled) {
}
