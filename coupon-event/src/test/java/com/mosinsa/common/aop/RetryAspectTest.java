package com.mosinsa.common.aop;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetryAspectTest {


	@Test
	void success() {

		AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new TestClient());
		aspectJProxyFactory.addAspect(new RetryAspect());
		TestClient proxy = aspectJProxyFactory.getProxy();

		proxy.successMethod();
		assertThat(proxy.getSuccessCount()).isEqualTo(1);
	}

	@Test
	void fail() {
		AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new TestClient());
		aspectJProxyFactory.addAspect(new RetryAspect());
		TestClient proxy = aspectJProxyFactory.getProxy();

		assertThrows(RetryFailException.class, proxy::failMethod);
		assertThat(proxy.getFailCount()).isEqualTo(3);
	}
}