package com.mosinsa.order.infra.jpa;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.command.domain.OrderRepository;
import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomOrderRepositoryImplTest {

    @Autowired
    OrderRepository repository;

    @Test
    void findOrdersByCondition() {
        SearchCondition searchCondition = new SearchCondition("customer1",null);
        Page<Order> ordersByCondition = repository.findOrdersByCondition(searchCondition, PageRequest.of(0, 3));
        List<Order> content = ordersByCondition.getContent();
        assertThat(content).hasSize(2);

        List<String> answers = List.of("orderId2", "orderId1");
        int idx=0;
        for (Order order : content) {
            assertThat(order.getId().getId()).isEqualTo(answers.get(idx++));
        }
    }

//    @Test
    void findOrdersByConditionAll() {
        SearchCondition searchCondition = new SearchCondition(null,null);
        Page<Order> ordersByCondition = repository.findOrdersByCondition(searchCondition, PageRequest.of(0, 3));
        List<Order> content = ordersByCondition.getContent();
        assertThat(content).hasSize(3);

        List<String> answers = List.of("orderId3", "orderId2", "orderId1");
        int idx=0;
        for (Order order : content) {
            assertThat(order.getId().getId()).isEqualTo(answers.get(idx++));
        }
    }

    @Test
    void findOrdersByConditionByStatus() {
        SearchCondition searchCondition = new SearchCondition(null, OrderStatus.PREPARING);
        Page<Order> ordersByCondition = repository.findOrdersByCondition(searchCondition, PageRequest.of(0, 3));
        List<Order> content = ordersByCondition.getContent();
        assertThat(content).hasSize(1);

        List<String> orderIds = content.stream().map(Order::getId).map(OrderId::getId).toList();
        assertThat(orderIds).containsExactly("orderId2");
    }
}