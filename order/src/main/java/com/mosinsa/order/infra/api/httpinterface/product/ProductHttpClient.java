package com.mosinsa.order.infra.api.httpinterface.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/product-service")
public interface ProductHttpClient {

	@PostExchange("/products/order")
	ResponseEntity<Void> orderProducts(@RequestBody OrderProductRequests request);
}
