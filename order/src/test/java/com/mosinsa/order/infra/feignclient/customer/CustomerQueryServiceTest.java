package com.mosinsa.order.infra.feignclient.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerQueryServiceTest {

    @Autowired
    CustomerQueryService service;

    @Test
    void customerCheckNoId() {
        assertThrows(IllegalArgumentException.class, () -> service.customerCheck(null, ""));
    }
}