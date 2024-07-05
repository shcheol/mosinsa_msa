package com.mosinsa.reaction.infra.kafka.events;

public record ReviewLikesEvent(String productId, String reviewId, boolean canceled) {
}
