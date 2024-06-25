package com.mosinsa.websocket.kafka;

public record CommentDislikesEvent(String productId, String reviewId, String commentId, boolean canceled) {
}
