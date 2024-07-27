package com.mosinsa.code;

import com.mosinsa.common.aop.RedissonLock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class LockTestClass {

    @RedissonLock(value = "1234223qkey", waitTime = 100L, leaseTime = 300L, timeUnit = TimeUnit.MILLISECONDS)
    public void method() throws InterruptedException {
        System.out.println("do something... start");
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println("do something... end");
    }
}