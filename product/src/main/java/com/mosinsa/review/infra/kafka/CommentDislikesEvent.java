package com.mosinsa.review.infra.kafka;

public record CommentDislikesEvent(String productId, String reviewId, String commentId, boolean canceled) {
}
