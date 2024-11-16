package com.mosinsa.order.infra.api.httpinterface;

import com.mosinsa.order.infra.api.RequestHeaderExtractor;
import com.mosinsa.order.infra.api.httpinterface.coupon.CouponHttpClient;
import com.mosinsa.order.infra.api.httpinterface.product.ProductHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
public class HttpInterfaceConfig {

	@Value("${mosinsa.base-url}")
	private String baseUrl;

	@Bean
	public CouponHttpClient couponHttpClient(RestClient restClient) {
		return HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build()
				.createClient(CouponHttpClient.class);
	}

	@Bean
	public ProductHttpClient productHttpClient(RestClient restClient) {
		return HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build()
				.createClient(ProductHttpClient.class);
	}

	@Bean
	public RestClient restClient() {

		ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
				.withConnectTimeout(Duration.ofSeconds(5))
				.withReadTimeout(Duration.ofSeconds(3));
		return RestClient.builder()
				.uriBuilderFactory(new DefaultUriBuilderFactory(baseUrl))
				.requestInterceptor((request, body, execution) -> {
					request.getHeaders().addAll(RequestHeaderExtractor.extract());
					return execution.execute(request, body);
				})
				.defaultStatusHandler(new ApiErrorHandler())
				.requestFactory(ClientHttpRequestFactories.get(clientHttpRequestFactorySettings))
				.build();
	}
}
