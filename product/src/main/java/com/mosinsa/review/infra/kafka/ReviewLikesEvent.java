package com.mosinsa.review.infra.kafka;

public record ReviewLikesEvent(String reviewId, boolean canceled) {
}
