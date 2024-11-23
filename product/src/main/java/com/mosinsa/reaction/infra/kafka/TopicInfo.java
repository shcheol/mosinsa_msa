package com.mosinsa.reaction.infra.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "mosinsa.topic")
public record TopicInfo(Map<String, String> reaction) {
}
