package com.mosinsa.product.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.product.query.dto.ProductSummary;
import com.mosinsa.product.query.dto.ProductDetails;
import com.mosinsa.product.query.ProductQueryService;
import com.mosinsa.product.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/products")
public class ViewProductController {
	private final ProductQueryService productQueryService;

	@GetMapping
	public ResponseEntity<Page<ProductSummary>> findAllProducts(SearchCondition condition, Pageable pageable) {

		Page<ProductSummary> allProducts = productQueryService.findProductsByCondition(condition, pageable);
		return ResponseEntity.ok(allProducts);
	}
	@GetMapping("/my")
	public ResponseEntity<Page<ProductSummary>> findMyProducts(@Login CustomerInfo customerInfo, Pageable pageable) {

		Page<ProductSummary> allProducts = productQueryService.findMyProducts(customerInfo.id(), pageable);
		return ResponseEntity.ok(allProducts);
	}


	@GetMapping("/{productId}")
	public ResponseEntity<ProductDetails> productDetails(@PathVariable String productId) {
		log.info("getProduct {}", productId);

		ProductDetails productDetails = productQueryService.getProductById(productId);
		return ResponseEntity.ok(productDetails);
	}
}
