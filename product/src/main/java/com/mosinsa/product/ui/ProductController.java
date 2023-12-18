package com.mosinsa.product.ui;

import com.mosinsa.product.application.ProductService;
import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.LikesProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
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

	@GetMapping("/{productId}")
	public ResponseEntity<BaseResponse> getProduct(@PathVariable String productId){
		log.info("getProduct {}", productId);

		ProductDto productDto = productService.getProductById(productId);
		return GlobalResponseEntity.ok(productDto);
	}

	@PatchMapping("/{productId}/likes")
	public ResponseEntity<BaseResponse> likes(@PathVariable String productId, @RequestBody LikesProductRequest request){

		if (!productId.equals(request.productId())){
			log.error("product Id match fail {}, {}", productId, request.productId());
			throw new ProductException(ProductError.VALIDATION_ERROR);
		}
		productService.likes(request);
		return ResponseEntity.ok().build();
	}

    @GetMapping
    public ResponseEntity<BaseResponse> findAllProducts(Pageable pageable){

		Page<ProductDto> allProducts = productService.getAllProducts(pageable);
		return GlobalResponseEntity.ok(allProducts);
	}

	@PostMapping("/order")
	public ResponseEntity<Void> orderProducts(@RequestBody List<OrderProductRequest> request){

		productService.orderProduct(request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/cancel")
	public ResponseEntity<Void> cancelOrderProducts(@RequestBody List<CancelOrderProductRequest> request){

		productService.cancelOrderProduct(request);

		return ResponseEntity.ok().build();
	}

    @PostMapping
    public ResponseEntity<BaseResponse> addProduct(@RequestBody CreateProductRequest request){

        ProductDto productDto = productService.createProduct(request);

		return GlobalResponseEntity.success(HttpStatus.CREATED, productDto);
    }

}
