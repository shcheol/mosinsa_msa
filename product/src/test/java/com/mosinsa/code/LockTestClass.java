package com.mosinsa.code;

import com.mosinsa.common.aop.RedissonLock;

import java.util.concurrent.TimeUnit;

public class LockTestClass {

    @RedissonLock(value = "qqq", waitTime = 500L, leaseTime = 2100L, timeUnit = TimeUnit.MILLISECONDS)
    public void method() throws InterruptedException {
        System.out.println("do something... start");
        TimeUnit.MILLISECONDS.sleep(1500);
        System.out.println("do something... end");
    }
}