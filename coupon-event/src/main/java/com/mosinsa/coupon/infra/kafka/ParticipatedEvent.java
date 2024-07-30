package com.mosinsa.coupon.infra.kafka;

public record ParticipatedEvent(String memberId, long couponGroupSequence) {
}
