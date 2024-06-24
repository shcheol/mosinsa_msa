package com.mosinsa.websocket.kafka;

public record CommentDislikesEvent(String reviewId, String commentId, boolean canceled) {
}
