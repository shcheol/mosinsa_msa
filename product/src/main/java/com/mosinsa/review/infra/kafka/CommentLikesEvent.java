package com.mosinsa.review.infra.kafka;

public record CommentLikesEvent(String productId, String reviewId, String commentId, boolean canceled) {
}
