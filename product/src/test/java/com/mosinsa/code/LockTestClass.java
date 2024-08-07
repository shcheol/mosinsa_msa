package com.mosinsa.code;

import com.mosinsa.common.aop.RedissonLock;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTestClass {

	private AtomicInteger cnt = new AtomicInteger(0);
	@RedissonLock(value = "testeky123", waitTime = 300L, leaseTime = 1000L, timeUnit = TimeUnit.MILLISECONDS)
	public void method() throws InterruptedException {
		System.out.println("do something... start" + LocalDateTime.now());
		cnt.addAndGet(1);
		TimeUnit.MILLISECONDS.sleep(800);
		System.out.println("do something... end" + LocalDateTime.now());
	}

	public int getCnt(){
		return cnt.get();
	}
}