package com.mosinsa.order.infra.api.feignclient.product;

import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.ui.request.MyOrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

	private final ProductFeignClient productFeignClient;

	public ResponseResult<ProductResponse> productCheck(Map<String, Collection<String>> headers, MyOrderProduct myOrderProduct) {

		return ResponseResult.execute(() -> productFeignClient.getProduct(headers, myOrderProduct.productId()).getResponse());
	}

}
