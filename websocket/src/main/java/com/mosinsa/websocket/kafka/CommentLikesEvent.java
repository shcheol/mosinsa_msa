package com.mosinsa.websocket.kafka;

public record CommentLikesEvent(String productId, String commentId, boolean canceled) {
}
