package com.mosinsa.reaction.infra.kafka.events;

public record ReviewDislikesEvent(String productId, String reviewId, boolean canceled) {
}
