package com.mosinsa.promotion.infra.kafka;

public record ParticipatedEvent(String memberId, long couponGroupSequence) {
}
