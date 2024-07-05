package com.mosinsa.reaction.infra.kafka.events;

public record CommentLikesEvent(String channel, String commentId, boolean canceled) {
}
