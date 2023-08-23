package com.mosinsa.order.service.feignclient;

import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.response.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "product-service", url = "localhost:8000/product-service")
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ResponseProduct getProduct(@RequestHeader Map<String, Collection<String>> headers, @PathVariable(value = "productId") String productId);

    @PostMapping("/products")
    ResponseProduct addProduct(@RequestBody ProductAddRequest request);
}
