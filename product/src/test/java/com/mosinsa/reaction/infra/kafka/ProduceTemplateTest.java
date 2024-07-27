package com.mosinsa.reaction.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProduceTemplateTest {

    @Autowired
    ProduceTemplate produceTemplate;

    @Test
    void product() {

        A a = new A();
        B b = new B();
        a.b =b;
        b.a = a;
        assertThrows(IllegalStateException.class, () -> produceTemplate.getPayloadFromObject(a));
    }

    static class A{
        public B b;
    }
    static class B{
        public A a;
    }
}