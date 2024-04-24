package com.mosinsa.order.query.application;

import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql("classpath:db/test-init.sql")
class OrderQueryServiceTest {

	@Autowired
    OrderQueryService orderQueryService;

    @Test
    void findMyOrdersByConditionEx() {
        SearchCondition searchCondition = new SearchCondition();
		Pageable pageable = Pageable.ofSize(2);
        assertThrows(OrderException.class, () -> orderQueryService.findMyOrdersByCondition(searchCondition, pageable));
    }

    @Test
    void getOrderDetails() {
        OrderDetail orderId2 = orderQueryService.getOrderDetails("orderId2");
        assertThat(orderId2.getOrderId()).isEqualTo("orderId2");
    }

    @Test
    void getOrderDetailsNotFound() {
        assertThrows(OrderException.class, () -> orderQueryService.getOrderDetails("orderId2xxx"));
    }

    @Test
    void getOrderDetailsEx() {
        assertThrows(IllegalArgumentException.class, () -> orderQueryService.getOrderDetails(""));
    }
}