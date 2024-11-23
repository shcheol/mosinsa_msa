package com.mosinsa.order.query.application;

import com.mosinsa.ApplicationTest;
import com.mosinsa.common.ex.OrderException;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class OrderQueryServiceImplTest extends ApplicationTest {

    @Autowired
    OrderQueryServiceImpl orderQueryService;

    @Test
    void findMyOrdersByConditionEx() {
        SearchCondition searchCondition1 = new SearchCondition("", null);
        PageRequest pageable = PageRequest.of(0, 3);
        assertThrows(OrderException.class, () -> orderQueryService.findMyOrdersByCondition(searchCondition1, pageable));

        SearchCondition searchCondition2 = new SearchCondition(null, null);
        assertThrows(OrderException.class, () -> orderQueryService.findMyOrdersByCondition(searchCondition2, pageable));
    }

    @Test
    void findMyOrdersByCondition() {
        SearchCondition searchCondition1 = new SearchCondition("customer1", null);
        PageRequest pageable = PageRequest.of(0, 3);
        Page<OrderSummary> myOrdersByCondition = orderQueryService.findMyOrdersByCondition(searchCondition1, pageable);
        assertThat(myOrdersByCondition.getTotalElements()).isEqualTo(2);
        List<String> ids = myOrdersByCondition.getContent().stream().map(OrderSummary::id).toList();
        assertThat(ids).contains("orderId1", "orderId2");
    }

    @Test
    void getOrderDetails() {
        OrderDetail orderId2 = orderQueryService.getOrderDetails("orderId2");
        assertThat(orderId2.id()).isEqualTo("orderId2");
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