package com.mosinsa.product.ui;

import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.query.ProductDetailDto;
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
}
