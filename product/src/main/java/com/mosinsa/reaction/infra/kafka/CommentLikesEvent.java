package com.mosinsa.reaction.infra.kafka;

public record CommentLikesEvent(String productId, String commentId, boolean canceled) {
}
