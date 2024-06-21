package com.mosinsa.review.infra.kafka;

public record CommentLikesEvent(String reviewId, String commentId, boolean canceled) {
}
