package com.mosinsa.order.infra.feignclient;

import com.mosinsa.order.ui.request.ProductAddRequest;
import com.mosinsa.order.ui.response.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "product-service", url = "${feignclient.url.product}")
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ResponseProduct getProduct(@RequestHeader Map<String, Collection<String>> headers, @PathVariable(value = "productId") String productId);

    @PostMapping("/products")
    ResponseProduct addProduct(@RequestBody ProductAddRequest request);
}
