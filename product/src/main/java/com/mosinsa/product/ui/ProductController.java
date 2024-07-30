package com.mosinsa.product.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequests;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/products")
public class 	ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductId> addProduct(@RequestBody CreateProductRequest request) {

        ProductId id = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/order")
    public ResponseEntity<Void> orderProducts(@RequestBody OrderProductRequests request, @Login CustomerInfo customerInfo) {

        log.info("orderProductRequests {}", request);
        productService.orderProduct(customerInfo.id(), request.orderId(), request.orderProductRequests());

        return ResponseEntity.ok().build();
    }

}
