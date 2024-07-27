package com.mosinsa.common.aop;

import com.mosinsa.code.LockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedissonLockAspectTest {

    @Autowired
    LockTest lockTest;

    @Test
    void test() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        CompletableFuture.runAsync(() -> {
            try {
                lockTest.method();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                countDownLatch.countDown();
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                assertThrows(TryLockFailException.class, () -> lockTest.method());
            } finally {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

    }



}