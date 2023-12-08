package com.mosinsa.product.ui;


import com.mosinsa.product.ui.request.ProductAddRequest;
import com.mosinsa.product.ui.request.ProductUpdateRequest;
import com.mosinsa.product.dto.ProductDto;
import com.mosinsa.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.setValidator(new ProductValidator());
	}

    @GetMapping
    public Page<ProductDto> findAllProducts(Pageable pageable){

		return productService.findAllProducts(pageable);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductAddRequest request){

        ProductDto productDto = productService.addProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
        log.info("getProduct {}", productId);
        ProductDto product = productService.findProductById(productId);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request){

        productService.updateProduct(productId, request);

        return ResponseEntity.ok().build();
    }


}
