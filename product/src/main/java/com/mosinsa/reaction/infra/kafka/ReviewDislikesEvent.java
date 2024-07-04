package com.mosinsa.reaction.infra.kafka;

public record ReviewDislikesEvent(String productId, String reviewId, boolean canceled) {
}
