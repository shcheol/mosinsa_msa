package com.mosinsa.reaction.infra.kafka;

public record CommentDislikesEvent(String productId, String commentId, boolean canceled) {
}
