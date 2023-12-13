package com.mosinsa.order.infra.feignclient;

import com.mosinsa.order.ui.response.ResponseProduct;
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
    ResponseProduct getProduct(@RequestHeader Map<String, Collection<String>> headers, @PathVariable(value = "productId") String productId);

	@PostMapping("/products/order")
	ResponseProduct orderProducts(@RequestBody List<OrderProductRequest> request);

	@PostMapping("/products/cancel")
	ResponseProduct cancelOrderProducts(@RequestBody List<CancelOrderProductRequest> request);
}
