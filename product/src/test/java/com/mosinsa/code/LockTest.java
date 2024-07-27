package com.mosinsa.code;

import com.mosinsa.common.aop.RedissonLock;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockTest{

    @RedissonLock(value = "1234223key", waitTime = 100L, leaseTime = 200L,timeUnit = TimeUnit.MILLISECONDS)
    public void method() throws InterruptedException {
        System.out.println("do something...");
        TimeUnit.MILLISECONDS.sleep(300L);
    }
}