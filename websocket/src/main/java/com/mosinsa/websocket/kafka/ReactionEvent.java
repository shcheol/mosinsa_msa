package com.mosinsa.websocket.kafka;


public record ReactionEvent(String channelId, DomainType target, String targetId, ReactionType reactionType, boolean canceled) {

}
