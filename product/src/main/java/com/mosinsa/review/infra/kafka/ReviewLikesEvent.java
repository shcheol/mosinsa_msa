package com.mosinsa.review.infra.kafka;

public record ReviewLikesEvent(String productId, String reviewId, boolean canceled) {
}
