package com.mosinsa.order.application;

import com.mosinsa.order.application.dto.OrderDetailDto;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderQueryServiceTest {

    @Autowired
    OrderQueryService orderQueryService;

    @Test
    void findMyOrdersByConditionEx() {
        SearchCondition searchCondition = new SearchCondition();

        assertThrows(OrderException.class, () -> orderQueryService.findMyOrdersByCondition(searchCondition, Pageable.ofSize(2)));
    }

    @Test
    void getOrderDetails() {
        OrderDetailDto orderId2 = orderQueryService.getOrderDetails("orderId2");
        assertThat(orderId2.getOrderId()).isEqualTo("orderId2");
    }

    @Test
    void getOrderDetailsNotFound() {
        assertThrows(OrderException.class, () -> orderQueryService.getOrderDetails("orderId2xxx"));
    }

    @Test
    void getOrderDetailsEx() {
        assertThrows(OrderException.class, () -> orderQueryService.getOrderDetails(""));
    }
}