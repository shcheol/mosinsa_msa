package com.mosinsa.order.infra.kafka;

import com.mosinsa.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ProduceTemplateTest extends ApplicationTest {

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