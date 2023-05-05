package com.shopping.mosinsa.controller;

import com.shopping.mosinsa.controller.request.ProductAddRequest;
import com.shopping.mosinsa.controller.request.ProductUpdateRequest;
import com.shopping.mosinsa.dto.ProductDto;
import com.shopping.mosinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public Page<ProductDto> getProducts(Pageable pageable){

        Page<ProductDto> products = productService.getProducts(pageable);

        return products;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductAddRequest request){

        ProductDto productDto = productService.addProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId){

        ProductDto product = productService.getProduct(productId);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request){

        productService.updateProduct(productId, request);

        return ResponseEntity.ok().build();
    }


}
