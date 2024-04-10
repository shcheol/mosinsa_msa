package com.mosinsa.common.aop;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

@Component
public class TestClass{

    private int successCount=0;
    private int failCount=0;

    @Retry(times = 3)
    void successMethod(){
        System.out.println("TestClass.successMethod");
        successCount++;
    }

    @Retry(times = 3)
    void failMethod(){
        System.out.println("TestClass.failMethod");
        failCount++;
        throw new RuntimeException();
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailCount() {
        return failCount;
    }
}
