package com.mosinsa.common.aop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RetryAspectTest {

    @Autowired TestClass testClass;

    @Test
    void success(){
        testClass.successMethod();
    }

    @Test
    void fail(){
        Assertions.assertThrows(RuntimeException.class, () -> testClass.failMethod());
    }
}