package com.mosinsa.common.aop;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

@Component
public class TestClass{
    @Retry(times = 3)
    void successMethod(){
        System.out.println("TestClass.successMethod");
    }

    @Retry(times = 3)
    void failMethod(){
        System.out.println("TestClass.failMethod");
        throw new RuntimeException();
    }
}
