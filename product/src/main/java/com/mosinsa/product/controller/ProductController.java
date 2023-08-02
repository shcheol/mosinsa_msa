package com.mosinsa.product.controller;


import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.db.dto.ProductDto;
import com.mosinsa.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.setValidator(new ProductValidator());
	}

    @GetMapping
    public Page<ProductDto> findAllProducts(Pageable pageable){

		return productServiceImpl.findAllProducts(pageable);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductAddRequest request){

        ProductDto productDto = productServiceImpl.addProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){

        ProductDto product = productServiceImpl.findProductById(productId);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request){

        productServiceImpl.updateProduct(productId, request);

        return ResponseEntity.ok().build();
    }


}
