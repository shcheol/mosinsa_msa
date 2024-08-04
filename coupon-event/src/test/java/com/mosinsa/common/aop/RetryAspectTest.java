package com.mosinsa.common.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RetryAspectTest {

    @Autowired
    TestClient testClient;

    @Test
    void success(){
        testClient.successMethod();
        assertThat(testClient.getSuccessCount()).isEqualTo(1);
    }

    @Test
    void fail(){
        assertThrows(RetryFailException.class, () -> testClient.failMethod());
        assertThat(testClient.getFailCount()).isEqualTo(3);
    }
}