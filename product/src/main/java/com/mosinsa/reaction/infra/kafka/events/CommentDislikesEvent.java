package com.mosinsa.reaction.infra.kafka.events;

public record CommentDislikesEvent(String productId, String commentId, boolean canceled) {
}
