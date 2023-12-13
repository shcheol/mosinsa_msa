package com.mosinsa.order.domain;

import com.mosinsa.order.common.ex.OrderException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

	@Test
	void create(){
		List<OrderProduct> orderProducts = List.of();
		assertThrows(OrderException.class, () -> Order.create("customerId", orderProducts));
	}
}