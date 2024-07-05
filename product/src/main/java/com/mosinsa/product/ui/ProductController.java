package com.mosinsa.product.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.query.ProductQueryService;
import com.mosinsa.product.ui.request.CancelOrderProductRequests;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequests;
import com.mosinsa.product.ui.request.SearchCondition;
import com.mosinsa.product.ui.response.BaseResponse;
import com.mosinsa.product.ui.response.GlobalResponseEntity;
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
	public ResponseEntity<BaseResponse> findAllProducts(SearchCondition condition, Pageable pageable) {

		Page<ProductQueryDto> allProducts = productQueryService.findProductsByCondition(condition, pageable);
		return GlobalResponseEntity.ok(allProducts);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<BaseResponse> productDetails(@PathVariable String productId) {
		log.info("getProduct {}", productId);

		ProductDetailDto productDetailDto = productQueryService.getProductById(productId);
		return GlobalResponseEntity.ok(productDetailDto);
	}

	@PostMapping
	public ResponseEntity<BaseResponse> addProduct(@RequestBody CreateProductRequest request) {

		ProductDetailDto productDetailDto = productService.createProduct(request);

		return GlobalResponseEntity.success(HttpStatus.CREATED, productDetailDto);
	}

	@PostMapping("/order")
	public ResponseEntity<Void> orderProducts(@RequestBody OrderProductRequests request, @Login CustomerInfo customerInfo) {

		log.info("orderProductRequests {}", request);
		productService.orderProduct(customerInfo.id(), request.orderId(), request.orderProductRequests());

		return ResponseEntity.ok().build();
	}

	@Deprecated
	@PostMapping("/cancel")
	public ResponseEntity<Void> cancelOrderProducts(@RequestBody CancelOrderProductRequests request, @Login CustomerInfo customerInfo) {

		productService.cancelOrderProduct(customerInfo.id(), "", request.cancelOrderProductRequests());

		return ResponseEntity.ok().build();
	}

}
