package com.mosinsa.order.infra.feignclient.product;

import com.mosinsa.order.ui.response.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "product-service", url = "${feignclient.url.product}")
public interface ProductClient {

	@GetMapping("/products/{productId}")
	GlobalResponse<ProductResponse> getProduct(@RequestHeader Map<String, Collection<String>> headers,
											   @PathVariable(value = "productId") String productId);

	@PostMapping("/products/order")
	Void orderProducts(@RequestHeader Map<String, Collection<String>> headers,
								  @RequestBody OrderProductRequests request);

	@PostMapping("/products/cancel")
	Void cancelOrderProducts(@RequestHeader Map<String, Collection<String>> headers,
										@RequestBody CancelOrderProductRequests request);
}
