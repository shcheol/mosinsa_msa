package com.mosinsa.review.infra.kafka;

public record ReviewDislikesEvent(String reviewId, boolean canceled) {
}
