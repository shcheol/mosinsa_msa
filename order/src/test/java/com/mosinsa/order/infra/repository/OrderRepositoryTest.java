package com.mosinsa.order.infra.repository;

import com.mosinsa.order.command.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("classpath:db/test-init.sql")
class OrderRepositoryTest {

	@Autowired
	OrderRepository repository;

	@Test
	void create() {
		Order order = Order.create("customerId",
				List.of(OrderProduct.create("productId", 1000, 10)),
				ShippingInfo.of(Address.of("","",""),"", Receiver.of("","")));
		Order saveOrder = repository.save(order);

		assertThat(order).isEqualTo(saveOrder);
	}


	@Test
	void cancelSuccess(){
		Order findOrder = repository.findById(OrderId.of("orderId2")).get();
		assertThat(findOrder.getStatus()).isNotEqualTo(OrderStatus.CANCELED);

		findOrder.cancelOrder();
		Order cancelOrder = repository.findById(findOrder.getId()).get();
		assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}

	@Test
	void cancelFail(){
		Order findOrder = repository.findById(OrderId.of("orderId3")).get();
		assertThat(findOrder.getStatus()).isNotEqualTo(OrderStatus.CANCELED);

		assertThrows(AlreadyShippedException.class, findOrder::cancelOrder);
	}
}