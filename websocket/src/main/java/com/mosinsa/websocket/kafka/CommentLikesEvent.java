package com.mosinsa.websocket.kafka;

public record CommentLikesEvent(String productId, String reviewId, String commentId, boolean canceled) {
}
