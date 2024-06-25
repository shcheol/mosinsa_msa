package com.mosinsa.review.infra.kafka;

public record ReviewDislikesEvent(String productId, String reviewId, boolean canceled) {
}
