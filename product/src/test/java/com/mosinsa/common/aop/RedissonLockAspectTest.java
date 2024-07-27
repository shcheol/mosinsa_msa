package com.mosinsa.common.aop;

import com.mosinsa.code.LockTestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(LockConfig.class)
class RedissonLockAspectTest {

    @Autowired
    LockTestClass lockTestClass;

    @Test
    void test() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        CompletableFuture.runAsync(() -> {
            try {
                lockTestClass.method();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                countDownLatch.countDown();
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                assertThrows(TryLockFailException.class, () -> lockTestClass.method());
            } finally {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

    }



}