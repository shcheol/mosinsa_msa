package com.mosinsa.product.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.query.ProductQueryService;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequests;
import com.mosinsa.product.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductQueryService productQueryService;
	private final ProductService productService;

	@GetMapping
	public ResponseEntity<Page<ProductQueryDto>> findAllProducts(SearchCondition condition, Pageable pageable) {

		Page<ProductQueryDto> allProducts = productQueryService.findProductsByCondition(condition, pageable);
		return ResponseEntity.ok(allProducts);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDetailDto> productDetails(@PathVariable String productId) {
		log.info("getProduct {}", productId);

		ProductDetailDto productDetailDto = productQueryService.getProductById(productId);
		return ResponseEntity.ok(productDetailDto);
	}

	@PostMapping
	public ResponseEntity<ProductDetailDto> addProduct(@RequestBody CreateProductRequest request) {

		ProductDetailDto productDetailDto = productService.createProduct(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(productDetailDto);
	}

	@PostMapping("/order")
	public ResponseEntity<Void> orderProducts(@RequestBody OrderProductRequests request, @Login CustomerInfo customerInfo) {

		log.info("orderProductRequests {}", request);
		productService.orderProduct(customerInfo.id(), request.orderId(), request.orderProductRequests());

		return ResponseEntity.ok().build();
	}

}
