package com.mosinsa.order.feignclient;

import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.response.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/product-service/products/{productId}")
    ResponseProduct getProduct(@PathVariable(value = "productId") Long productId);

    @PostMapping("/product-service/products")
    ResponseProduct addProduct(@RequestBody ProductAddRequest request);
}
