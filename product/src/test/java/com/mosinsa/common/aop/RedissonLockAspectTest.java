package com.mosinsa.common.aop;

import com.mosinsa.code.LockTestClass;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

class RedissonLockAspectTest {

	@Test
	void test() throws InterruptedException {

		AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new LockTestClass());
		Config config = new Config();
		config.useSingleServer().setAddress("redis://150.230.252.178:6666");
		aspectJProxyFactory.addAspect(new RedissonLockAspect(Redisson.create(config)));

		LockTestClass proxy = aspectJProxyFactory.getProxy();

		CountDownLatch countDownLatch = new CountDownLatch(2);
		CompletableFuture.runAsync(() -> {
			try {
				proxy.method();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				countDownLatch.countDown();
			}
		});
		try {
			proxy.method();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e).isInstanceOf(TryLockFailException.class);
		} finally {
			countDownLatch.countDown();
		}

		countDownLatch.await();
		assertThat(proxy.getCnt()).isEqualTo(1);

	}


}