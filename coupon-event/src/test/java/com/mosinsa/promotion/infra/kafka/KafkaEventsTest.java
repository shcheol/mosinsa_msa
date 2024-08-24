package com.mosinsa.promotion.infra.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KafkaEventsTest {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void noKafkaTemplate(){
        KafkaEvents.setKafkaTemplate(null);
        KafkaEvents.raise("","");

        KafkaEvents.setKafkaTemplate(kafkaTemplate);
    }

}