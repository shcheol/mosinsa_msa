package com.mosinsa.order.infra.api.feignclient.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@FeignClient(name = "product-service", url = "${feignclient.url.product}")
public interface ProductClient {

	@GetMapping("/products/{productId}")
	ProductResponse getProduct(@RequestHeader Map<String, Collection<String>> headers,
							   @PathVariable(value = "productId") String productId);

	@PostMapping("/products/order")
	void orderProducts(@RequestHeader Map<String, Collection<String>> headers,
					   @RequestBody OrderProductRequests request);
}
