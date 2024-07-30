package com.mosinsa.order.ui;

import com.mosinsa.order.query.application.OrderQueryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OrderApplicationObjectFactory {

	@Bean
	public OrderQueryService orderQueryService(){
		return new OrderQueryServiceStub();
	}
}
