package com.mosinsa.order.infra.feignclient.product;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "product-service", url = "${feignclient.url.product}")
public interface ProductClient {

	@GetMapping("/products/{productId}")
	Response getProduct(@RequestHeader Map<String, Collection<String>> headers,
						@PathVariable(value = "productId") String productId);

	@PostMapping("/products/order")
	ResponseResult<Void> orderProducts(@RequestHeader Map<String, Collection<String>> headers,
								  @RequestBody OrderProductRequests request);

	@PostMapping("/products/cancel")
	ResponseResult<ProductResponse> cancelOrderProducts(@RequestHeader Map<String, Collection<String>> headers,
										@RequestBody CancelOrderProductRequests request);
}
