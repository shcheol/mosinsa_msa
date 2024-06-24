package com.mosinsa.websocket.kafka;

public record CommentLikesEvent(String reviewId, String commentId, boolean canceled) {
}
