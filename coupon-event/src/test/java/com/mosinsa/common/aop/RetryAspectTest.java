package com.mosinsa.common.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RetryAspectTest {

    @Autowired TestClass testClass;

    @Test
    void success(){
        testClass.successMethod();
        assertThat(testClass.getSuccessCount()).isEqualTo(1);
    }

    @Test
    void fail(){
        assertThrows(RetryFailException.class, () -> testClass.failMethod());
        assertThat(testClass.getFailCount()).isEqualTo(3);
    }
}