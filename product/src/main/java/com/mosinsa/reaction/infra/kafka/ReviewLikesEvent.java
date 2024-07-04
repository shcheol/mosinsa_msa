package com.mosinsa.reaction.infra.kafka;

public record ReviewLikesEvent(String productId, String reviewId, boolean canceled) {
}
