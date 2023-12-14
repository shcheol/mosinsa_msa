package com.mosinsa.order.infra.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "product-service", url = "${feignclient.url.product}")
public interface ProductClient {

	@GetMapping("/products/{productId}")
	ProductResponse getProduct(@RequestHeader Map<String, Collection<String>> headers,
							   @PathVariable(value = "productId") String productId);

	@PostMapping("/products/order")
	ProductResponse orderProducts(@RequestHeader Map<String, Collection<String>> headers,
								  @RequestBody List<OrderProductRequest> request);

	@PostMapping("/products/cancel")
	ProductResponse cancelOrderProducts(@RequestHeader Map<String, Collection<String>> headers,
										@RequestBody List<CancelOrderProductRequest> request);
}
