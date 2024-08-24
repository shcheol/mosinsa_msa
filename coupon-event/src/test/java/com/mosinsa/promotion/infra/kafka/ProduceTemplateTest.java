package com.mosinsa.promotion.infra.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProduceTemplateTest {

    @Autowired
    ProduceTemplate produceTemplate;

    @Test
    void product() {

        A a = new A();
        B b = new B();
        a.b = b;
        b.a = a;
        assertThrows(IllegalStateException.class, () -> produceTemplate.getPayloadFromObject(a));
    }

    static class A {
        public B b;
    }

    static class B {
        public A a;
    }

}