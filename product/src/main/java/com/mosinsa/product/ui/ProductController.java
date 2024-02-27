package com.mosinsa.product.ui;

import com.mosinsa.product.application.ProductService;
import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.application.dto.ProductQueryDto;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.ui.request.*;
import com.mosinsa.product.ui.response.BaseResponse;
import com.mosinsa.product.ui.response.GlobalResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

	@GetMapping
	public ResponseEntity<BaseResponse> findAllProducts(SearchCondition condition, Pageable pageable){

		Page<ProductQueryDto> allProducts = productService.findProductsByCondition(condition, pageable);
		return GlobalResponseEntity.ok(allProducts);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<BaseResponse> productDetails(@PathVariable String productId){
		log.info("getProduct {}", productId);

		ProductDetailDto productDetailDto = productService.getProductById(productId);
		return GlobalResponseEntity.ok(productDetailDto);
	}


	@GetMapping("/likes")
	public ResponseEntity<BaseResponse> likesProducts(@RequestParam String customer){

		List<ProductDetailDto> myLikesProducts = productService.findMyLikesProducts(customer);
		return GlobalResponseEntity.ok(myLikesProducts);
	}

	@PostMapping("/{productId}/likes")
	public ResponseEntity<BaseResponse> likes(@PathVariable String productId, @RequestBody LikesProductRequest request){

		if (!productId.equals(request.productId())){
			log.error("product Id match fail {}, {}", productId, request.productId());
			throw new ProductException(ProductError.VALIDATION_ERROR);
		}
		productService.likes(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<BaseResponse> addProduct(@RequestBody CreateProductRequest request){

		ProductDetailDto productDetailDto = productService.createProduct(request);

		return GlobalResponseEntity.success(HttpStatus.CREATED, productDetailDto);
	}

	@PostMapping("/order")
	public ResponseEntity<Void> orderProducts(@RequestBody OrderProductRequests request){

		log.info("orderProductRequests {}", request);
		productService.orderProduct(request.orderProductRequests());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/cancel")
	public ResponseEntity<Void> cancelOrderProducts(@RequestBody CancelOrderProductRequests request){

		productService.cancelOrderProduct(request.cancelOrderProductRequests());

		return ResponseEntity.ok().build();
	}

}
