package com.mosinsa.order.infra.feignclient.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class ProductFallback implements ProductClient {

	@Override
	public ProductResponse getProduct(Map<String, Collection<String>> headers, String productId) {
		log.warn("product service not available, try later");
		return null;
	}

	@Override
	public void orderProducts(Map<String, Collection<String>> headers, OrderProductRequests request) {
		log.warn("product service not available, try later");
	}

	@Override
	public ProductResponse cancelOrderProducts(Map<String, Collection<String>> headers, CancelOrderProductRequests request) {
		log.warn("product service not available, try later");
		return null;
	}
}
