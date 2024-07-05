package com.mosinsa.reaction.infra.kafka.events;

public record ProductLikesEvent(String channel, boolean canceled) {
}
