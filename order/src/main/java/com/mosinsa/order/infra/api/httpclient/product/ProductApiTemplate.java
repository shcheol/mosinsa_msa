package com.mosinsa.order.infra.api.httpclient.product;

import com.mosinsa.order.infra.api.feignclient.product.CancelOrderProductRequests;
import com.mosinsa.order.infra.api.feignclient.product.OrderProductRequests;
import com.mosinsa.order.infra.api.feignclient.product.ProductResponse;
import com.mosinsa.order.infra.api.httpclient.ApiExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductApiTemplate {

	private final ApiExecutor apiExecutor;

	@Value(value = "${feignclient.url.product}")
	private String uri;


	public ProductResponse getProduct(Map<String, Collection<String>> headers, String productId) {


		try {
			apiExecutor.execute(new URI(uri));
		}catch (IOException e){

		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public void orderProducts(Map<String, Collection<String>> headers, OrderProductRequests request) {

	}

	public void cancelOrderProducts(Map<String, Collection<String>> headers, CancelOrderProductRequests request) {

	}
}
