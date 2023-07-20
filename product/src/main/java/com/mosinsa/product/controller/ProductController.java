package com.mosinsa.product.controller;


import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.dto.ProductDto;
import com.mosinsa.product.service.ProductService;
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
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){

        ProductDto product = productService.getProduct(productId);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request){

        productService.updateProduct(productId, request);

        return ResponseEntity.ok().build();
    }


}
