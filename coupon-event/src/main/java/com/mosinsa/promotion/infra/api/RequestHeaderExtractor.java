package com.mosinsa.promotion.infra.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

public class RequestHeaderExtractor {

	private static final List<String> requiredHeaderKeys = List.of(
			HttpHeaders.AUTHORIZATION,
			HeaderConst.REFRESH_TOKEN.key(),
			HeaderConst.CUSTOMER_INFO.key()
	);


	public static HttpHeaders extract() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpHeaders headers = new HttpHeaders();
		requiredHeaderKeys.stream()
				.filter(rhk -> StringUtils.hasText(request.getHeader(rhk)))
				.forEach(requiredHeaderKey -> headers.put(requiredHeaderKey, List.of(request.getHeader(requiredHeaderKey))));
		return headers;
	}

	private RequestHeaderExtractor() {
	}
}
