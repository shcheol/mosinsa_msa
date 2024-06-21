package com.mosinsa.review.infra.kafka;

public record CommentDislikesEvent(String reviewId, String commentId, boolean canceled) {
}
