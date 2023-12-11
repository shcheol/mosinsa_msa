package com.mosinsa.product.ui;


import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.application.dto.ProductDto;
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

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
		log.info("getProduct {}", productId);
		ProductDto product = productService.getProductById(productId);

		return ResponseEntity.ok().body(product);
	}

    @GetMapping
    public Page<ProductDto> findAllProducts(Pageable pageable){

		return productService.getAllProducts(pageable);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody CreateProductRequest request){

        ProductDto productDto = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

}
