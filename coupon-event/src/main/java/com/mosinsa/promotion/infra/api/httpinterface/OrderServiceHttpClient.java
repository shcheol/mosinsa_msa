package com.mosinsa.promotion.infra.api.httpinterface;

import com.mosinsa.promotion.infra.api.CustomPageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/order-service")
public interface OrderServiceHttpClient {

	@GetExchange("/orders")
	ResponseEntity<CustomPageImpl<OrderSummary>> myOrders(@RequestParam("customerId") String customerId);
}
